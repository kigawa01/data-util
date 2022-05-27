package net.kigawa.data.javatype;

import net.kigawa.data.database.DatabaseType;

public interface JavaDataInterface
{
    Object getData();

    void setData(Object data);

    DatabaseType getDatabaseType();
}
