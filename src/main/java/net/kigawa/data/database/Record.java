package net.kigawa.data.database;

import net.kigawa.data.data.JavaData;
import net.kigawa.kutil.kutil.Kutil;
import net.kigawa.kutil.kutil.list.GenerateMap;
import net.kigawa.kutil.log.log.Logger;

import java.sql.ResultSet;
import java.util.Arrays;


public class Record {
    private final Columns columns;
    private final JavaData key;
    private final Logger logger;
    private final Table table;
    private final GenerateMap<Column, Field> fieldMap;

    protected Record(Logger logger, Table table, Columns columns, JavaData key) {
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

    public int update(String[] columns, JavaData... javaData) {
        var list = Arrays.asList(javaData);
        list.add(key);

        if (columns.length < 1) {
            logger.warning("column is not exist!");
            return -1;
        }
        for (int i = 0; i < columns.length; i++) {
            if (!this.columns.contain(columns[i])) {
                logger.warning("column: " + columns[i] + " is not exist");
                return -1;
            }
            if (!this.columns.get(i).getSqlDataType().isAllow(javaData[i])) {
                logger.warning("data is not allowed");
                return -1;
            }
            if (this.columns.getKeyName().equals(columns[i])
                    && key.equals(javaData[i])) {
                logger.warning("key is not able to change");
                return -1;
            }
        }

        return table.update(columns, this.columns.getKeyName() + "=?", Kutil.getArrangement(list, JavaData[]::new));
    }

    public ResultSet select(String[] columns) {
        if (columns.length < 1) {
            logger.warning("column is not exist!");
            return null;
        }
        for (String column : columns) {
            if (!this.columns.contain(column)) {
                logger.warning("column: " + column + " is not exist");
                return null;
            }
        }
        return table.select(columns, this.columns.getKeyName() + "=?", key);
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

    public boolean equalsKey(JavaData key) {
        return this.key.equals(key);
    }


}
