package net.kigawa.data.javatype;

import net.kigawa.data.database.DatabaseField;

public interface JavaField
{
    Object getData();

    void setData(Object data);

    DatabaseField getDatabaseType();
}
