package net.kigawa.data.database;

import net.kigawa.data.data.JavaData;
import net.kigawa.kutil.kutil.list.GenerateMap;
import net.kigawa.kutil.log.log.Logger;

import java.sql.ResultSet;

public class Table {
    private final Database database;
    private final String name;
    private final Columns columns;
    private final Logger logger;
    private final GenerateMap<JavaData, Record> recordMap;

    protected Table(Logger logger, Database dataBase, String name, Columns columns) {
        this.database = dataBase;
        this.name = name;
        this.columns = columns;
        this.logger = logger;
        recordMap = new GenerateMap<>(data -> new Record(logger, this, columns, data));
    }

    public void createConnection() {
        database.createConnection();
    }

    public void close() {
        database.close();
    }

    public int update(String[] columns, String where, JavaData... data) {
        return database.update(name, columns, where, data);
    }

    public int insert(String[] columns, JavaData... data) {
        return database.insert(name, columns, data);
    }

    public ResultSet select(String[] columns, String where, JavaData... data) {
        return database.select(name, columns, where, data);
    }

    public void removeRecord(JavaData key) {
        recordMap.remove(key);
    }

    public Record getRecord(JavaData key) {
        return recordMap.get(key);
    }

    public Database getDatabase() {
        return database;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object o) {
        if (o instanceof Table) return equalsTable((Table) o);
        return false;
    }

    public boolean equalsTable(Table table) {
        return equalsDatabase(table.database) && equalsName(table.name);
    }

    public boolean equalsName(String name) {
        return this.name.equals(name);
    }

    public boolean equalsColumn(Columns columns) {
        return this.columns.equals(columns);
    }

    public boolean equalsDatabase(Database database) {
        return this.database.equals(database);
    }
}
