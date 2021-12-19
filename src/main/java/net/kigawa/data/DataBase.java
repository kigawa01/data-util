package net.kigawa.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class DataBase implements Iterable<Table> {
    private final String name;
    private final List<Table> tableList = new ArrayList<>();

    protected DataBase(String name) {
        this.name = name;
    }

    public boolean equals(String name){
        return this.name.equals(name);
    }

    @Override
    public Iterator<Table> iterator() {
        return tableList.listIterator();
    }

    @Override
    public void forEach(Consumer<? super Table> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Table> spliterator() {
        return Iterable.super.spliterator();
    }
}
