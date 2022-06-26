package net.kigawa.data.mysql;

import net.kigawa.data.database.AbstractDatabase;
import net.kigawa.data.database.DataHolderMeta;
import net.kigawa.data.database.Field;
import net.kigawa.data.database.SqlBuilder;
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
    private final Logger logger;
    private Connection connection;

    public Mysql(String url, String database, Logger logger)
    {
        this.url = url;
        this.database = database;
        this.logger = logger;
        this.databaseUrl = url + "/" + database;
    }

    @Override
    protected void createConnection()
    {
        try {
            if (connection != null && connection.isClosed()) return;

            connection = DriverManager.getConnection(databaseUrl);

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
    protected <T> void createTable(DataHolderMeta<T> dataHolderMeta)
    {
        var sql = new SqlBuilder()
                .add("CREATE").add("TABLE").add("IF").add("NOT").add("EXISTS").add(dataHolderMeta.getName())
                .add("(");

        for ()
    }

    @Override
    protected <T> void deleteTable(DataHolderMeta<T> dataHolderMeta)
    {
        exec(() -> {
            var st = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + dataHolderMeta.getName()
            );
        });
    }

    @Override
    protected <T> T load(DataHolderMeta<T> recordClass, Object keyValue)
    {
        exec(() -> {
            var st = connection.prepareStatement("SELECT * FROM ? WHERE ?=?");
            st.setString(1, recordClass.getName());
            st.setString(2, recordClass.primaryKey.getName());
            st.setObject(3, recordClass.primaryKey.get(keyValue));
        });
    }

    @Override
    protected <T> void save(DataHolderMeta<T> dataHolderMeta, T dataHolder)
    {

    }

    @Override
    protected <T> void delete(DataHolderMeta<T> dataHolderMeta, Object keyValue)
    {

    }

    @Override
    protected <T> List<T> loadFrom(DataHolderMeta<T> dataHolderMeta, Field... keys)
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
