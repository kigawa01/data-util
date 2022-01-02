package net.kigawa.data.database;

import net.kigawa.data.sql.Column;

public class Data<T> {
    private final Column label;
    private final Recorde column;
    private T data;

    protected Data(Recorde column, Column label, T data) {
        this.data = data;
        this.label = label;
        this.column = column;
    }

    public Column getLabel() {
        return label;
    }

    public T getData() {
        return data;
    }

    public Data setData(T data) {
        this.data = data;
        return this;
    }
}
