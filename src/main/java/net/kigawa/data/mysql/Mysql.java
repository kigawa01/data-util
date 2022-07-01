package net.kigawa.data.mysql;

import net.kigawa.data.database.AbstractDatabase;
import net.kigawa.data.database.DatabaseField;
import net.kigawa.data.database.TableInfo;
import net.kigawa.data.exception.DatabaseException;
import net.kigawa.data.sql.SqlBuilder;
import net.kigawa.data.util.TogetherTwo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
    protected <T> void createTable(TableInfo<T> tableMeta)
    {
        var sql = new SqlBuilder()
                .add("CREATE").add("TABLE").add("IF").add("NOT").add("EXISTS").add(tableMeta.name)
                .add("(");

        for (var databaseField : tableMeta) {
            sql.add(databaseField.getName())
                    .add(databaseField.getTypeName())
                    .add("DEFAULT").add(databaseField)
                    .add(databaseField.getStrOptions()).add(",");
        }
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
        sql.add("WHERE").add(tableInfo.primaryKey.getName()).add("=").add(tableInfo.primaryKey);


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
        return null;
    }

    @Override
    protected <T> void save(TableInfo<T> tableInfo, T dataHolder)
    {

    }

    @Override
    protected <T> void delete(TableInfo<T> tableInfo, Object keyValue)
    {

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
