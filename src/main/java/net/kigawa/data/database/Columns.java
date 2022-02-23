package net.kigawa.data.database;

public class Columns {
    private final Column[] labels;

    public Columns(Column... labels) {
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
