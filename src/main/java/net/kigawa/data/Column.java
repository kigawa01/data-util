package net.kigawa.data;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Column implements Iterable<Data> {
    private final Labels labels;
    private final Data[] data;
    int iterator=0;

    public Column(Labels labels, Data... data) {
        this.labels = labels;
        this.data = new Data[labels.size()];

        for (int i = 0; i < labels.size(); i++) {
            setData(i, data[i]);
        }

        for (Data column:this){

        }
    }

    public void setData(int index, Data data) {
        this.data[index] = data;
    }

    @Override
    public Iterator<Data> iterator() {
        return null;
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
