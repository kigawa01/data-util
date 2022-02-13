package net.kigawa.data.database;

import net.kigawa.data.sql.ColumnList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Table implements Iterable<Recorde> {
    private final ColumnList labels;
    private final List<Recorde> columnList = new ArrayList<>();
    private final String name;
    private final Database dataBase;

    protected Table(String name, Database dataBase, ColumnList labels, Recorde... columns) {
        this.dataBase = dataBase;
        this.name = name;
        this.labels = labels;
        Collections.addAll(columnList, columns);
    }

    public boolean equals(String name) {
        return this.name.equals(name);
    }

    @Override
    public Iterator<Recorde> iterator() {
        return columnList.iterator();
    }
}
