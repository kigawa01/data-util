package net.kigawa.data.database;

import net.kigawa.data.data.Data;
import net.kigawa.data.data.DataType;
import net.kigawa.data.data.ExtraType;
import net.kigawa.data.data.KeyType;

public class Column {
    private final String name;
    private final DataType dataType;
    private final boolean canNull;
    private final KeyType keyType;
    private final Data defaultData;
    private final ExtraType extraType;

    protected Column(String name, DataType dataType, boolean canNull, KeyType keyType, Data defaultData, ExtraType extraType) {
        this.name = name;
        this.dataType = dataType;
        this.canNull = canNull;
        this.keyType = keyType;
        this.defaultData = defaultData;
        this.extraType = extraType;
    }

    public ExtraType getExtraType() {
        return extraType;
    }

    public Data getDefaultData() {
        return defaultData;
    }

    public KeyType getKeyType() {
        return keyType;
    }

    public boolean canNull() {
        return canNull;
    }

    public String getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public boolean equals(Column column) {
        if (!column.name.equals(name)) return false;
        return true;
    }
}
