package net.kigawa.data;

import java.util.Arrays;
import java.util.Iterator;

public class Column implements Iterable<Data> {
    private final Labels labels;
    private final Data[] data;
    private final Table table;

    protected Column(Table table, Labels labels, Data... dataArray) {
        this.table = table;
        this.labels = labels;
        this.data = dataArray;
    }

    @Override
    public Iterator<Data> iterator() {
        return Arrays.stream(data).iterator();
    }
}
