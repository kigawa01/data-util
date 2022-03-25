package net.kigawa.data.database;

import net.kigawa.data.javadata.JavaData;
import net.kigawa.data.extratype.ExtraType;
import net.kigawa.data.keytype.KeyType;

public class Column {
    private final String name;
    private final SqlDataType<? extends JavaData> sqlDataType;
    private final boolean canNull;
    private final KeyType keyType;
    private final JavaData defaultData;
    private final ExtraType extraType;

    protected Column(String name, SqlDataType<? extends JavaData> sqlDataType, boolean canNull, KeyType keyType, JavaData defaultData, ExtraType extraType) {
        this.name = name;
        this.sqlDataType = sqlDataType;
        this.canNull = canNull;
        this.keyType = keyType;
        this.defaultData = defaultData;
        this.extraType = extraType;
    }

    public KeyType getKeyType() {
        return keyType;
    }

    public SqlDataType<? extends JavaData> getSqlDataType() {
        return sqlDataType;
    }

    public String getOptionSql(){
        //todo
        return "";
    }

    public String getName() {
        return name;
    }

    public boolean equals(Column column) {
        if (!column.name.equals(name)) return false;
        return true;
    }
}
