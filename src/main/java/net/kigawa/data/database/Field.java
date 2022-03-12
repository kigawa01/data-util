package net.kigawa.data.database;

import net.kigawa.data.data.Data;

public class Field {
    private final Column column;
    private final Record record;
    private Data data;

    protected Field(Record record, Column column, Data data) {
        this.data = data;
        this.column = column;
        this.record = record;
    }

    public Column getColumn() {
        return column;
    }

    public Data getData() {
        return data;
    }

    public Field setData(Data data) {
        this.data = data;
        return this;
    }
}
