package net.kigawa.data.database;

import net.kigawa.kutil.kutil.interfaces.Module;
import net.kigawa.kutil.log.log.Logger;

import java.util.LinkedList;
import java.util.List;


public class DataBaseManager implements Module {
    public static Logger logger;
    private final List<Database> databases = new LinkedList<>();

    public DataBaseManager(Logger logger) {
        DataBaseManager.logger = logger;
    }

    public Database getDatabase(String url, String name, boolean migrate) {
        var database = new Database(logger, url, name, migrate);
        databases.add(database);
        return database;
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
