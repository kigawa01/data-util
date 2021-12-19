package net.kigawa.data;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Column implements Iterable<Data> {
    private final Labels labels;
    private final Data[] data;
    private final Table table;

    protected Column(Table table, Labels labels, Data... dataArray) {
        this.table = table;
        this.labels = labels;
        this.data = new Data[labels.size()];

        for (Data data : dataArray) setData(data);
    }

    public void setData(Data data) {
        this.data[labels.getIndex(data.getLabel())] = data;
    }

    @Override
    public Iterator<Data> iterator() {
        return Arrays.stream(data).iterator();
    }

    @Override
    public void forEach(Consumer<? super Data> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Data> spliterator() {
        return Iterable.super.spliterator();
    }
}
