package net.kigawa.data.database;

import net.kigawa.data.data.JavaData;
import net.kigawa.kutil.kutil.list.GenerateMap;
import net.kigawa.kutil.log.log.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public int delete(String where, JavaData... javaData) {
        return database.delete(name, where, javaData);
    }

    public int update(String[] columns, String where, JavaData... javaData) {
        return database.update(name, columns, where, javaData);
    }

    public int insert(String[] columns, JavaData... javaData) {
        return database.insert(name, columns, javaData);
    }

    public ResultSet select(String[] columns, String where, JavaData... javaData) {
        return database.select(name, columns, where, javaData);
    }

    public void removeRecord(JavaData key) {
        recordMap.remove(key);
    }

    public Record getRecord(JavaData key, boolean create) {
        if (create) insertDefault(key);
        return recordMap.get(key);
    }

    public void insertDefault(JavaData key) {
        try {
            createConnection();
            var result = select(new String[]{columns.getKeyName()}, columns.getKeyName() + "=?", key);
            if (result.next()) {
                close();
                return;
            }
            insert(new String[]{columns.getKeyName()}, key);
        } catch (SQLException e) {
            logger.warning(e);
        }
        close();
    }

    public void delete(Record record) {
        delete(columns.getKeyName() + "=?", record.getKey());
        removeRecord(record.getKey());
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
