package net.kigawa.data.database;

public class Data<T> {
    private final Column label;
    private final Record column;
    private T data;

    protected Data(Record column, Column label, T data) {
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
