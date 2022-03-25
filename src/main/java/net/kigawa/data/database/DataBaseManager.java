package net.kigawa.data.database;

import net.kigawa.kutil.kutil.interfaces.Module;
import net.kigawa.kutil.log.log.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class DataBaseManager implements Module {
    public static Logger logger;
    private final List<Database> databases = new LinkedList<>();

    public DataBaseManager(Logger logger) {
        DataBaseManager.logger = logger;
    }

    public Database getDatabase(String url, String name, boolean create) {
        if (create) createDataBase(url, name);
        var database = new Database(logger, url, name);
        databases.add(database);
        return database;
    }

    public void createDataBase(String url, String name) {
        try {
            var connection = DriverManager.getConnection(url);
            connection.prepareStatement("CREATE DATABASE IF NOT EXISTS " + name).executeUpdate();
        } catch (SQLException e) {
            logger.warning(e);
        }
    }

    public void dropDataBase(String url, String name) {
        try {
            var connection = DriverManager.getConnection(url);
            connection.prepareStatement("DROP DATABASE IF EXISTS " + name).executeUpdate();
        } catch (SQLException e) {
            logger.warning(e);
        }
    }

    protected void removeDatabase(Database database) {
        databases.remove(database);
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {
        for (var database : databases) database.close();
        databases.clear();
    }
}
