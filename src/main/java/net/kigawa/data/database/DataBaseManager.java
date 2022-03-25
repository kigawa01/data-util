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
            logger.fine("execute sql:");
            connection.prepareStatement(logger.finePass("CREATE DATABASE IF NOT EXISTS " + name)).executeUpdate();
        } catch (SQLException e) {
            logger.warning(e);
        }
    }

    public void dropDatabase(String url, String name) {
        try {
            var connection = DriverManager.getConnection(url);
            logger.fine("execute sql:");
            connection.prepareStatement(logger.finePass("DROP DATABASE IF EXISTS " + name)).executeUpdate();
        } catch (SQLException e) {
            logger.warning(e);
        }
    }

    public void dropDatabase(Database database) {
        dropDatabase(database.getUrl(), database.getName());
        removeDatabase(database);
    }

    public void removeDatabase(Database database) {
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
