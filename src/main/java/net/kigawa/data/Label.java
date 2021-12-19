package net.kigawa.data;

public class Label<T> {
    private String name;
    private Class<T> javaType;
    private String dbType;

    protected Label() {
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public boolean equals(Label label) {
        if (!label.name.equals(name)) return false;
        if (!label.javaType.equals(javaType)) return false;
        if (!label.dbType.equals(dbType)) return false;
        return true;
    }
}
