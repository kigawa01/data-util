package net.kigawa.data;

public class Labels {
    private final Label[] labels;

    public Labels(Label... labels) {
        this.labels = labels;
    }

    public int size() {
        return labels.length;
    }

    public Label getLabel(int index) {
        return labels[index];
    }

    public Label[] getLabels() {
        return labels;
    }
}
