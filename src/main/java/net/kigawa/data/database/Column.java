package net.kigawa.data.database;

import net.kigawa.data.javadata.JavaData;
import net.kigawa.data.keytype.KeyType;

public class Column {
    private final String name;
    private final SqlDataType<? extends JavaData> sqlDataType;
    private final boolean canNull;
    private final KeyType keyType;
    private final JavaData defaultData;
    private final Extra extra;

    public Column(String name, SqlDataType<? extends JavaData> sqlDataType) {
        this(name, sqlDataType, true, null, null, null);
    }

    public Column(String name, SqlDataType<? extends JavaData> sqlDataType, boolean canNull, KeyType keyType, JavaData defaultData, Extra extra) {
        this.name = name;
        this.sqlDataType = sqlDataType;
        this.canNull = canNull;
        this.keyType = keyType;
        this.defaultData = defaultData;
        this.extra = extra;
    }

    public KeyType getKeyType() {
        return keyType;
    }

    public SqlDataType<? extends JavaData> getSqlDataType() {
        return sqlDataType;
    }

    public JavaData getDefaultData() {
        return defaultData;
    }

    public String getOptionSql() {
        var sb = new StringBuffer();
        if (keyType != null) sb.append(keyType.getSql()).append(" ");
        if (!canNull) sb.append("NOT NULL ");
        if (defaultData != null) sb.append("DEFAULT ? ");
        if (extra != null) sb.append(extra.getSql()).append(" ");
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public boolean equals(Column column) {
        if (!column.name.equals(name)) return false;
        return true;
    }
}
