package net.kigawa.data.database;

import net.kigawa.data.data.Data;
import net.kigawa.kutil.kutil.Kutil;
import net.kigawa.kutil.kutil.list.GenerateMap;
import net.kigawa.kutil.log.log.Logger;

import java.sql.ResultSet;
import java.util.Arrays;


public class Record {
    private final Columns columns;
    private final Data key;
    private final Logger logger;
    private final Table table;
    private final GenerateMap<Column, Field> fieldMap;

    protected Record(Logger logger, Table table, Columns columns, Data key) {
        this.columns = columns;
        this.key = key;
        this.logger = logger;
        this.table = table;
        fieldMap = new GenerateMap<>(column -> new Field(this, column));
    }

    public void createConnection() {
        table.createConnection();
    }

    public void close() {
        table.close();
    }

    public int update(String[] columns, Data... data) {
        var list = Arrays.asList(data);
        list.add(key);
        return table.update(columns, this.columns.getKeyName() + "=?", Kutil.getArrangement(list, Data[]::new));
    }

    public int insert(String[] columns, Data... data) {
        return table.insert(columns, data);
    }

    public ResultSet select(String[] columns, Data... data) {
        var list = Arrays.asList(data);
        list.add(key);
        return table.select(columns, this.columns.getKeyName() + "=?", Kutil.getArrangement(list, Data[]::new));
    }

    public Field getField(String name) {
        if (!columns.contain(name)) return null;
        return fieldMap.get(columns.get(name));
    }

    public void removeField(Column column) {
        fieldMap.remove(column);
    }

    public Logger getLogger() {
        return logger;
    }

    @Override
    public int hashCode() {
        return table.hashCode()
                + key.hashCode()
                ;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Record) {
            return equalsRecord((Record) o);
        }
        return false;
    }

    public boolean equalsRecord(Record record) {
        if (record == null) return false;
        return equalsTable(record.table)
                && equalsKey(record.key)
                ;
    }

    public boolean equalsTable(Table table) {
        return this.table.equalsTable(table);
    }

    public boolean equalsKey(Data key) {
        return this.key.equals(key);
    }
}
