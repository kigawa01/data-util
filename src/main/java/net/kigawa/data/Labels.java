package net.kigawa.data;

public class Labels {
    private final Label[] labels;

    public Labels(Label... labels) {
        this.labels = labels;
    }

    public int getIndex(Label label) {
        for (int i = 0; i < size(); i++) {
            if (label.equals(labels[i])) return i;
        }
        return -1;
    }

    public int size() {
        return labels.length;
    }

    public Label getLabel(int index) {
        return labels[index];
    }
}
