package net.kigawa.data.javatype;

import net.kigawa.data.database.DatabaseTypeField;

public interface JavaDataInterface
{
    Object getData();

    void setData(Object data);

    DatabaseTypeField getDatabaseType();
}
