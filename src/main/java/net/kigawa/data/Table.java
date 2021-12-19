package net.kigawa.data;

import java.util.*;
import java.util.function.Consumer;

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

    @Override
    public Iterator<Column> iterator() {
        return columnList.iterator();
    }

    @Override
    public void forEach(Consumer<? super Column> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Column> spliterator() {
        return Iterable.super.spliterator();
    }
}
