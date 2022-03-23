package net.kigawa.data.database;

import net.kigawa.data.keytype.PrimaryKey;
import net.kigawa.kutil.log.log.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Columns implements Iterable<Column> {
    private final Map<String, Column> columnMap = new HashMap<>();

    public Columns(Logger logger, Column... columns) {
        for (Column column : columns) {
            columnMap.put(column.getName(), column);
        }
        int count = 0;
        for (Column column : columns) {
            if (column.getKeyType().equals(PrimaryKey.NAME)) count++;
        }
        if (count < 1) logger.warning("there is no key");
        if (count > 1) logger.warning("key is too many");
    }

    public String getKeyName() {

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
