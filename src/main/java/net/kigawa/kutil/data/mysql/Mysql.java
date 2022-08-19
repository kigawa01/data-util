package net.kigawa.kutil.data.mysql;

import net.kigawa.kutil.data.database.AbstractDatabase;
import net.kigawa.kutil.data.database.DatabaseField;
import net.kigawa.kutil.data.database.TableInfo;
import net.kigawa.kutil.data.exception.DatabaseException;
import net.kigawa.kutil.data.sql.SqlBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class Mysql extends AbstractDatabase
{
    private final String url;
    private final String database;
    private final String databaseUrl;
    private final String user;
    private final String password;
    private final Logger logger;
    private Connection connection;

    public Mysql(String url, String database, String user, String password, Logger logger)
    {
        this.url = url;
        this.database = database;
        this.user = user;
        this.password = password;
        this.logger = logger;
        this.databaseUrl = url + "/" + database;


    }

    @Override
    protected void createConnection()
    {
        try {
            if (connection != null && connection.isClosed()) return;

            connection = DriverManager.getConnection(databaseUrl, user, password);

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    protected void removeConnection()
    {
        try {
            if (connection == null || connection.isClosed()) return;
            connection.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }


    @Override
    protected <T> void createTable(TableInfo<T> tableInfo)
    {
        var sql = new SqlBuilder()
                .add("CREATE").add("TABLE").add("IF").add("NOT").add("EXISTS").add(tableInfo.name)
                .add("(");

        for (var fieldInfo : tableInfo) {
            sql.add(fieldInfo.name)
                    .add(resolveTypeName(fieldInfo))
                    .add("DEFAULT").addField(fieldInfo)
                    .add(fieldInfo.getOptions()).add(",");
        }

        for (var databaseOptions : tableInfo.getOptions()) {
            sql.add("CONSTRAINT").add(databaseOptions.name)
                    .add("FOREIGN").add("KEY").add("(").add(databaseOptions.getColumnName()).add(")")
                    .add("REFERENCES").add(databaseOptions.getParentName())
                    .add("(").add(databaseOptions.getParentColumnName()).add(")");
            sql.add(databaseOptions.getOptions()).add(",");
        }

        sql.removeLatestSql();
        sql.add(")");
        try {
            connect();
            sql.getStatement(connection).executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            close();
        }
    }

    @Override
    protected <T> void deleteTable(TableInfo<T> tableInfo)
    {
        var sql = new SqlBuilder()
                .add("DROP").add("TABLE").add("IF").add("EXISTS").add(tableInfo.name);
        try {
            connect();
            sql.getStatement(connection).executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            close();
        }

    }

    @Override
    protected <T> T load(TableInfo<T> tableInfo, Object keyValue)
    {
        var sql = new SqlBuilder()
                .add("SELECT").add("*").add("FROM").add(tableInfo.name);
        tableInfo.primaryKey.setValue(keyValue);
        sql.add("WHERE").add(tableInfo.primaryKey.name).add("=").addField(tableInfo.primaryKey);


        try {
            connect();
            var result = sql.getStatement(connection).executeQuery();
            if (!result.next()) return null;

            for (DatabaseField databaseField : tableInfo) {
                databaseField.readResult(result);
            }

            return tableInfo.record;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            close();
        }
    }

    @Override
    protected <T> List<T> loadWhere(TableInfo<T> tableInfo, SqlBuilder where)
    {
        var sql = new SqlBuilder()
                .add("SELECT").add("*").add("FROM").add(tableInfo.name)
                .add("WHERE").add(where);
        try {
            connect();
            var recordList = new LinkedList<T>();
            var result = sql.getStatement(connection).executeQuery();
            while (result.next()) {
                var tableInfo1 = tableInfo.getNewTableInfo();
                for (DatabaseField databaseField : tableInfo1) {
                    databaseField.readResult(result);
                }
                recordList.add(tableInfo.record);
            }
            return recordList;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    protected <T> void save(TableInfo<T> tableInfo, T dataHolder)
    {
        var sql = new SqlBuilder()
                .add("INSERT").add("INTO").add(tableInfo.name);
        var labels = new SqlBuilder();
        var values = new SqlBuilder();
        var duplicate = new SqlBuilder();
        for (DatabaseField databaseField : tableInfo) {
            labels.add(databaseField.name).add(",");
            values.addField(databaseField).add(",");
            duplicate.add(databaseField.name).add("=").addField(databaseField).add(",");
        }
        labels.removeLatestSql();
        values.removeLatestSql();
        duplicate.removeLatestSql();

        sql.add("(").add(labels).add(")")
                .add("VALUES").add("(").add(values).add(")")
                .add("ON").add("DUPLICATE").add("KEY").add("UPDATE").add(duplicate);

        try {
            connect();
            sql.getStatement(connection).executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            close();
        }
    }

    @Override
    protected <T> void delete(TableInfo<T> tableInfo, Object keyValue)
    {
        tableInfo.primaryKey.setValue(keyValue);
        var sql = new SqlBuilder()
                .add("DELETE").add("FROM").add(tableInfo.name)
                .add("WHERE").add(tableInfo.primaryKey.name).add("=").addField(tableInfo.primaryKey);
        try {
            connect();
            sql.getStatement(connection).executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            close();
        }
    }

    @Override
    public void enable()
    {
        try {
            var connection = DriverManager.getConnection(url);
            logger.info("check database");
            var st = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS ?");
            st.setString(1, database);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void disable()
    {
        removeConnection();
    }
}
