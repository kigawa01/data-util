package net.kigawa.data.database;

import net.kigawa.log.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public Database(String url, String name, boolean createDB) {
        this.url = url;
        this.name = name;

        if (createDB) createDB();
    }

    //------------------------------------------------------------------------------------------------------------------

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

    public Table getTable(String name, Columns columns) {
        for (Table table : tableSet) {
            if (table.getName().equals(name) && table.getDatabase().equals(this)) return table;
        }
        var table = new Table(this, name, columns);
        tableSet.add(table);
        return table;
    }

    public void createDB() {
        Logger.getInstance().info("create DB \"" + name + "\"");
        try {
            getPreparedStatement("create database if not exists " + name).executeUpdate();
            getPreparedStatement("use " + name).executeUpdate();
        } catch (SQLException e) {
            Logger.getInstance().warning(e);
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    private void createConnection() {
        session = 0;
        try {
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            Logger.getInstance().warning(e);
            connection = null;
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    public boolean equalsURL(String url) {
        return this.url.equals(url);
    }
}
