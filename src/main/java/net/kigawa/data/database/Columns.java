package net.kigawa.data.database;

import net.kigawa.data.javadata.JavaData;
import net.kigawa.data.keytype.PrimaryKey;
import net.kigawa.kutil.kutil.Kutil;
import net.kigawa.kutil.log.log.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * @deprecated
 */
public class Columns implements Iterable<Column> {
    private final Map<String, Column> columnMap = new HashMap<>();
    private String keyName;

    public Columns(Logger logger, Column... columns) {
        this(logger, true, columns);
    }

    public Columns(Logger logger, boolean keyCheck, Column... columns) {
        for (Column column : columns) {
            columnMap.put(column.getName(), column);
        }
        int count = 0;
        for (Column column : columns) {
            if (column.getKeyType() != null && column.getKeyType().equals(PrimaryKey.NAME)) {
                keyName = column.getName();
                count++;
            }
        }
        if (keyCheck && count < 1) logger.warning("there is no key");
        if (keyCheck && count > 1) logger.warning("key is too many");
    }

    public JavaData[] getDefaultData() {
        var list = new LinkedList<JavaData>();
        for (Column column : columnMap.values()) {
            if (column.getDefaultData() != null) list.add(column.getDefaultData());
        }
        return Kutil.getArrangement(list, JavaData[]::new);
    }

    public int size() {
        return columnMap.size();
    }

    public int getKeyIndex() {
        int i = 0;
        for (String key : columnMap.keySet()) {
            if (key.equals(keyName)) return i;
            i++;
        }
        return -1;
    }

    public String getKeyName() {
        return keyName;
    }

    public Column get(int i) {
        int i1 = 0;
        for (Column column : this) {
            if (i1 == i) return column;
            i1++;
        }
        return null;
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
