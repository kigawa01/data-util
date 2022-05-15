package net.kigawa.data.mysql;

import net.kigawa.data.database.AbstractDatabase;
import net.kigawa.data.database.DataHolderMeta;
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
    protected void createTable(DataHolderMeta dataHolderMeta)
    {
        exec(() -> {
            var st = connection.prepareStatement("CREATE TABLE IF NOT EXISTS ?");
            st.setString(1, dataHolderMeta.getName());
        });
    }

    @Override
    protected void deleteTable(DataHolderMeta dataHolderMeta)
    {
        exec(() -> {
            var st = connection.prepareStatement("CREATE TABLE IF NOT EXISTS ?");
            st.setString(1, dataHolderMeta.getName());
        });
    }

    @Override
    protected void load(DataHolderMeta dataHolderMeta, Object dataHolder)
    {
        exec(() -> {
            var st = connection.prepareStatement("SELECT * FROM ? WHERE ?=?");
            st.setString(1, dataHolderMeta.getName());
            st.setString(2, dataHolderMeta.primaryKey.getName());
            st.setObject(3, dataHolderMeta.primaryKey.get(dataHolder));
        });
    }

    @Override
    protected void save(DataHolderMeta dataHolderMeta, Object dataHolder)
    {

    }

    @Override
    protected void loadFrom(DataHolderMeta dataHolderMeta, List<?> dataHolder, String... keys)
    {

    }

    @Override
    protected void delete(DataHolderMeta dataHolderMeta, Object dataHolder)
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
