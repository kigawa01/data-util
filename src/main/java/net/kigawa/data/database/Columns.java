package net.kigawa.data.database;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Columns implements Iterable<Column> {
    private final Map<String, Column> columnMap = new HashMap<>();

    public Columns(Column... columns) {
        for (Column column : columns) {
            columnMap.put(column.getName(), column);
        }
    }

    public Column get(String name) {
        return columnMap.get(name);
    }

    public boolean contain(String name) {
        return columnMap.containsKey(name);
    }

    @Override
    public Iterator<Column> iterator() {
        return columnMap.values().iterator();
    }
}
