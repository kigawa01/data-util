package net.kigawa.data.database;

public class Column<T> {
    private String name;
    private Class<T> javaType;
    private String dbType;

    protected Column() {
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public boolean equals(Column label) {
        if (!label.name.equals(name)) return false;
        if (!label.javaType.equals(javaType)) return false;
        if (!label.dbType.equals(dbType)) return false;
        return true;
    }
}
