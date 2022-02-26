package net.kigawa.data.database;

public class Table {
    private final Database database;
    private final String name;
    private final Columns columns;

    Table(Database dataBase, String name, Columns columns, boolean migrate) {
        this.database = dataBase;
        this.name = name;
        this.columns = columns;
    }

    public boolean canUse() {

    }

    public void migrate() {

    }

    public void createTable() {
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
