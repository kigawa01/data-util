package net.kigawa.data.database;

import net.kigawa.util.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String url;
    private final List<Table> tableList = new ArrayList<>();

    protected Database(String url) {
        this.url = url;
    }

    public boolean equalsURL(String url) {
        return this.url.equals(url);
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            Logger.getInstance().warning(e);
            return null;
        }
    }

    public void unregister() {
        DatabaseManager.
    }
}
