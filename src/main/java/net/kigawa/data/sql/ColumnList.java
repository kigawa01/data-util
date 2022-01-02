package net.kigawa.data.sql;

public class ColumnList {
    private final Column[] labels;

    public ColumnList(Column... labels) {
        this.labels = labels;
    }

    void onSelect(StringBuffer sb){

    }

    public int getIndex(Column label) {
        for (int i = 0; i < size(); i++) {
            if (label.equals(labels[i])) return i;
        }
        return -1;
    }

    public int size() {
        return labels.length;
    }

    public Column getLabel(int index) {
        return labels[index];
    }
}
