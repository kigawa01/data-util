package net.kigawa.data.database;

import net.kigawa.log.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Database {
    private final String url;
    private final String name;
    private final Set<Table> tableSet = new HashSet<>();
    private Connection connection;
    private int session;

    public Database(String url, String name) {
        this(url, name, true);
    }

    public Database(String url, String name, boolean migrate) {
        this.url = url;
        this.name = name;

        if (migrate) migrate();

        close();
    }


    public void migrate() {
        Logger.getInstance().info("migrate DB \"" + name + "\"");
        if (canUse()) return;
        createDB();
    }

    public void createDB() {
        Logger.getInstance().info("create DB \"" + name + "\"");
        try {
            getPreparedStatement("CREATE DATABASE IF NOT EXIST " + name).executeUpdate();
            getPreparedStatement("use " + name).executeUpdate();
        } catch (SQLException e) {
            Logger.getInstance().warning(e);
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
            Logger.getInstance().warning(e);
        }
    }

    public PreparedStatement getPreparedStatement(String sql) {
        try {
            return getConnection().prepareStatement(sql);
        } catch (Exception e) {
            Logger.getInstance().warning(e);
            return null;
        }
    }

    public synchronized Connection getConnection() {
        try {
            session++;
            if (connection == null || connection.isClosed()) createConnection();
            return connection;
        } catch (Exception e) {
            Logger.getInstance().warning(e);
            return null;
        }
    }

    private void createConnection() {
        session = 0;
        try {
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            Logger.getInstance().warning(e);
            connection = null;
        }
    }

    public boolean canUse() {
        try {
            ResultSet resultSet = getPreparedStatement("SELECT database()").executeQuery();
            return (name.equalsIgnoreCase(resultSet.getString("database()")));
        } catch (SQLException e) {
            Logger.getInstance().warning(e);
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
        Logger.getInstance().info("delete DB \"" + name + "\"");
        try {
            getPreparedStatement("DROP DATABASE IF EXIST " + name).executeUpdate();
            close();
        } catch (SQLException e) {
            Logger.getInstance().warning(e);
        }
    }


    public boolean equalsURL(String url) {
        return this.url.equals(url);
    }
}
