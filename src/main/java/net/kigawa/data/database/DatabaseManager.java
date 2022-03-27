package net.kigawa.data.database;

import net.kigawa.kutil.kutil.interfaces.Module;
import net.kigawa.kutil.log.log.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class DatabaseManager implements Module {
    public static Logger logger;
    private final List<Database> databases = new LinkedList<>();

    public DatabaseManager(Logger logger) {
        DatabaseManager.logger = logger;
    }

    public Database getDatabase(
            String type,
            String userName,
            String password,
            String host,
            int port,
            String databaseName,
            boolean create
    ) {
        return getDatabase(
                "jdbc:" + type + "://" + host + ":" + port + "/",
                userName,
                password,
                databaseName,
                create
        );
    }

    public Database getDatabase(String url, String user, String password, String databaseName, boolean create) {
        if (create) createDataBase(url, databaseName, user, password);
        var database = new Database(logger, url, user, password, databaseName);
        databases.add(database);
        return database;
    }

    public void createDataBase(String url, String user, String password, String name) {
        try {
            logger.fine("connect " + url + " by " + user);
            var connection = DriverManager.getConnection(url, user, password);
            logger.fine("execute sql:");
            connection.prepareStatement(logger.finePass("CREATE DATABASE IF NOT EXISTS " + name)).executeUpdate();
        } catch (SQLException e) {
            logger.warning(e);
        }
    }

    public void dropDatabase(String url, String user, String password, String name) {
        try {
            logger.fine("connect " + url + " by " + user);
            var connection = DriverManager.getConnection(url, user, password);
            logger.fine("execute sql:");
            connection.prepareStatement(logger.finePass("DROP DATABASE IF EXISTS " + name)).executeUpdate();
        } catch (SQLException e) {
            logger.warning(e);
        }
    }

    public void dropDatabase(Database database) {
        dropDatabase(database.getUrl(), database.getUser(), database.getPassword(), database.getName());
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
