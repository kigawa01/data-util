package net.kigawa.data.database;

public class Column {
    private String name;
    private String dbType;

    protected Column() {
    }


    public boolean equals(Column label) {
        if (!label.name.equals(name)) return false;
        if (!label.dbType.equals(dbType)) return false;
        return true;
    }
}
