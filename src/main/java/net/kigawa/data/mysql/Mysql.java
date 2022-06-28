package net.kigawa.data.mysql;

import net.kigawa.data.database.AbstractDatabase;
import net.kigawa.data.database.Field;
import net.kigawa.data.database.SqlBuilder;
import net.kigawa.data.database.TableMeta;
import net.kigawa.data.exception.DatabaseException;

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
    protected <T> void createTable(TableMeta<T> tableMeta)
    {
        var sql = new SqlBuilder()
                .add("CREATE").add("TABLE").add("IF").add("NOT").add("EXISTS").add(tableMeta.getName())
                .add("(");

        for (var fieldMeta : tableMeta) {
            sql.add(fieldMeta.getName());
            var databaseType = fieldMeta.getEmptyDatabaseTypeField();
            sql.add(databaseType.getStrType()).add(databaseType.getStrOptions()).add(",");
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
    protected <T> void deleteTable(TableMeta<T> tableMeta)
    {
        var sql = new SqlBuilder()
                .add("DROP").add("TABLE").add("IF").add("EXISTS").add(tableMeta.getName());
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
    protected <T> T load(TableMeta<T> recordClass, Object keyValue)
    {
        exec(() -> {
            var st = connection.prepareStatement("SELECT * FROM ? WHERE ?=?");
            st.setString(1, recordClass.getName());
            st.setString(2, recordClass.primaryKey.getName());
            st.setObject(3, recordClass.primaryKey.get(keyValue));
        });
    }

    @Override
    protected <T> void save(TableMeta<T> tableMeta, T dataHolder)
    {

    }

    @Override
    protected <T> void delete(TableMeta<T> tableMeta, Object keyValue)
    {

    }

    @Override
    protected <T> List<T> loadFrom(TableMeta<T> tableMeta, Field... keys)
    {
        return null;
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
