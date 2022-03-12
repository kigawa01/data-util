package net.kigawa.data.database;

import net.kigawa.kutil.log.log.Logger;

import java.sql.SQLException;

public class Table {
    private final Database database;
    private final String name;
    private final Columns columns;
    private final Logger logger;

    protected Table(Logger logger, Database dataBase, String name, Columns columns, boolean migrate) {
        this.database = dataBase;
        this.name = name;
        this.columns = columns;
        this.logger = logger;

        if (migrate) migrate();
    }

    public boolean canUse() {
        var result = database.executeQuery("SHOW TABLES LIKE " + name);
        if (result == null) return false;
        try {
            if (result.next()) return true;
        } catch (SQLException e) {
            logger.warning(e);
        }
        return false;
    }

    public void migrate() {
        logger.info("migrate table: " + name);
        if (!canUse()) return;
        createTable();
    }

    public void createTable() {
        logger.info("create table: " + name);
        database.executeUpdate("CREATE TABLE IF NOT EXIST " + name);
        database.close();
    }

    public void deleteTable() {

    }

    public Database getDatabase() {
        return database;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object o) {
        if (o instanceof Table) return equals((Table) o);
        return false;
    }

    public boolean equals(Table table) {
        return equals(table.database) && equals(table.name);
    }

    public boolean equals(String name) {
        return this.name.equals(name);
    }

    public boolean equals(Database database) {
        return this.database.equals(database);
    }
}
