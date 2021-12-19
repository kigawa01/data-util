package net.kigawa.data;

public class Label {
    private final String name;
    private final DataType dataType;

    public Label(String name, DataType dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public DataType getDataType() {
        return dataType;
    }

    public String getName() {
        return name;
    }
}
