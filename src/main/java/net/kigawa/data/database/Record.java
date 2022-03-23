package net.kigawa.data.database;

import net.kigawa.kutil.kutil.list.GenerateMap;
import net.kigawa.kutil.log.log.Logger;


public class Record {
    private final Columns columns;
    private final WhereSql whereSql;
    private final Logger logger;
    private final Table table;
    private final GenerateMap<Column, Field> fieldMap;

    protected Record(Logger logger, Table table, Columns columns, WhereSql whereSql) {
        this.columns = columns;
        this.whereSql = whereSql;
        this.logger = logger;
        this.table = table;
        fieldMap = new GenerateMap<>(column -> new Field(this, column));
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
                + whereSql.hashCode()
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
        return equalsTable(record.table)
                && equalsWhere(record.whereSql)
                ;
    }

    public boolean equalsTable(Table table) {
        return this.table.equalsTable(table);
    }

    public boolean equalsWhere(WhereSql whereSql) {
        return this.whereSql.equalsWhereSql(whereSql);
    }
}
