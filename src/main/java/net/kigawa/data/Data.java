package net.kigawa.data;

public class Data<T> {
    private final Label label;
    private final Column column;
    private T data;

    protected Data(Column column, Label label, T data) {
        this.data = data;
        this.label = label;
        this.column = column;
    }

    public Label getLabel() {
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
