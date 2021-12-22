package net.kigawa.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Table implements Iterable<Column> {
    private final Labels labels;
    private final List<Column> columnList = new ArrayList<>();
    private final String name;
    private final DataBase dataBase;

    protected Table(String name, DataBase dataBase, Labels labels, Column... columns) {
        this.dataBase = dataBase;
        this.name = name;
        this.labels = labels;
        Collections.addAll(columnList, columns);
    }

    public boolean equals(String name) {
        return this.name.equals(name);
    }

    public Column createColumn(){

    }

    @Override
    public Iterator<Column> iterator() {
        return columnList.iterator();
    }
}
