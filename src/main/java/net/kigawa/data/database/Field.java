package net.kigawa.data.database;

public class Field<T> {
    private final Column column;
    private final Record record;
    private T data;

    protected Field(Record record, Column column, T data) {
        this.data = data;
        this.column = column;
        this.record = record;
    }

    public Column getColumn() {
        return column;
    }

    public T getData() {
        return data;
    }

    public Field setData(T data) {
        this.data = data;
        return this;
    }
}
