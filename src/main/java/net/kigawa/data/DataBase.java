package net.kigawa.data;

import java.util.Iterator;
import java.util.function.Consumer;

public class DataBase implements Iterator<Table> {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Table next() {
        return null;
    }

    @Override
    public void remove() {
        Iterator.super.remove();
    }

    @Override
    public void forEachRemaining(Consumer<? super Table> action) {
        Iterator.super.forEachRemaining(action);
    }
}
