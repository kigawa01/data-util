package net.kigawa.data.database;

import net.kigawa.kutil.kutil.KutilArray;

public class Columns {
    private final Column[] columns;

    public Columns(Column... columns) {
        this.columns = columns;
    }

    void onSelect(StringBuffer sb) {

    }

    public int getIndex(Column column) {
        for (int i = 0; i < size(); i++) {
            if (column.equals(columns[i])) return i;
        }
        return -1;
    }

    public boolean contain(Column column) {
        return KutilArray.contain(columns, column);
    }

    public int size() {
        return columns.length;
    }

    public Column getLabel(int index) {
        return columns[index];
    }
}
