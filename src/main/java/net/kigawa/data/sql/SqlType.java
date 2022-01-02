package net.kigawa.data.sql;

public enum SqlType {
    MySQL(""),
    ;
    private final String className;

    SqlType(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
