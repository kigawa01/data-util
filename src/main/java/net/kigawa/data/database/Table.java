package net.kigawa.data.database;

import net.kigawa.data.sql.Column;

public class Table {
    private final Database database;
    private final String name;
    private final Column[] columns;

    Table(Database dataBase, String name, Column... columns) {
        this.database = dataBase;
        this.name = name;
        this.columns = columns;
    }

    public Database getDatabase() {
        return database;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object o) {
        if (o instanceof Table) {
            var src = (Table) o;
            return database.equals(src.database) && name.equals(src.name);
        }
        return false;
    }
}
