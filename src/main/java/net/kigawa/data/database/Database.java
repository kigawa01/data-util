package net.kigawa.data.database;

import net.kigawa.kutil.log.log.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Database {
    private final String url;
    private final String name;
    private final Set<Table> tableSet = new HashSet<>();
    private final Logger logger;
    private Connection connection;
    private int session;

    protected Database(Logger logger, String url, String name, boolean migrate) {
        this.url = url;
        this.name = name;
        this.logger = logger;

        if (migrate) migrate();

        close();
    }


    public void migrate() {
        logger.info("migrate DB \"" + name + "\"");
        if (canUse()) return;
        createDB();
    }

    public void createDB() {
        logger.info("create DB \"" + name + "\"");
        try {
            createConnection();
            getPreparedStatement("CREATE DATABASE IF NOT EXIST " + name).executeUpdate();
            getPreparedStatement("use " + name).executeUpdate();
            close();
        } catch (SQLException e) {
            logger.warning(e);
        }
    }

    public synchronized void close() {
        try {
            session--;
            if (session > 0) return;
            if (connection == null) return;
            if (!connection.isClosed()) connection.close();
            connection = null;
        } catch (SQLException e) {
            logger.warning(e);
        }
    }

    public PreparedStatement getPreparedStatement(String sql) {
        try {
            return getConnection().prepareStatement(sql);
        } catch (Exception e) {
            logger.warning(e);
            return null;
        }
    }

    public synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) logger.warning("connection is closed!");
            return connection;
        } catch (Exception e) {
            logger.warning(e);
            return null;
        }
    }


    private void createConnection() {
        session = 0;
        try {
            session++;
            if (connection == null || connection.isClosed())
                connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            logger.warning(e);
            connection = null;
        }
    }

    public boolean canUse() {
        try {
            ResultSet resultSet = getPreparedStatement("SELECT database()").executeQuery();
            if (!resultSet.next()) return false;
            return (name.equalsIgnoreCase(resultSet.getString("database()")));
        } catch (SQLException e) {
            logger.warning(e);
            return false;
        }
    }

    public Table getTable(String name, Columns columns, boolean migrate) {
        for (Table table : tableSet) {
            if (table.getName().equals(name) && table.getDatabase().equals(this)) return table;
        }
        var table = new Table(this, name, columns, migrate);
        tableSet.add(table);
        return table;
    }


    public void deleteDB() {
        logger.info("delete DB \"" + name + "\"");
        try {
            getPreparedStatement("DROP DATABASE IF EXIST " + name).executeUpdate();
            close();
        } catch (SQLException e) {
            logger.warning(e);
        }
    }


    public boolean equalsURL(String url) {
        return this.url.equals(url);
    }
}
