package net.kigawa.data;

import java.util.Iterator;
import java.util.function.Consumer;

public class Table implements Iterator<Column> {

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Column next() {
        return null;
    }

    @Override
    public void remove() {
        Iterator.super.remove();
    }

    @Override
    public void forEachRemaining(Consumer<? super Column> action) {
        Iterator.super.forEachRemaining(action);
    }
}
