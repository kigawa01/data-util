package net.kigawa.data.database;

import net.kigawa.kutil.kutil.interfaces.Module;
import net.kigawa.kutil.log.log.Logger;


public class DataBaseManager implements Module {
    public static Logger logger;

    public DataBaseManager(Logger logger) {
        DataBaseManager.logger = logger;
    }

    public Database getDatabase(String url, String name, boolean migrate) {
        return new Database(logger, url, name, migrate);
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
