package net.kigawa.data.database;

import net.kigawa.data.sql.ColumnList;

import java.util.Arrays;
import java.util.Iterator;

public class Recorde implements Iterable<Data> {
    private final ColumnList labels;
    private final Data[] data;
    private final Table table;

    protected Recorde(Table table, ColumnList labels, Data... dataArray) {
        this.table = table;
        this.labels = labels;
        this.data = dataArray;
    }

    @Override
    public Iterator<Data> iterator() {
        return Arrays.stream(data).iterator();
    }
}
