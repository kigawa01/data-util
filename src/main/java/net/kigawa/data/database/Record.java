package net.kigawa.data.database;

import net.kigawa.data.data.Data;
import net.kigawa.kutil.kutil.list.GenerateMap;
import net.kigawa.kutil.log.log.Logger;


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
        fieldMap = new GenerateMap<>(column -> new Field( this, column));
    }

    public Field getField(Column column) {
        if (!columns.contain(column)) return null;
        return fieldMap.get(column);
    }

    public void removeField(Column column) {
        fieldMap.remove(column);
    }

    public Logger getLogger() {
        return logger;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Record) {
            return equalsRecord((Record) o);
        }
        return false;
    }

    public boolean equalsRecord(Record record) {
        return equalsTable(record.table) && equalsKey(record.key);
    }

    public boolean equalsTable(Table table) {
        return this.table.equalsTable(table);
    }

    public boolean equalsKey(Data key) {
        return this.key.equalsData(key);
    }
}
