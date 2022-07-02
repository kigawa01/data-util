package net.kigawa.data.javatype;

import net.kigawa.data.database.DatabaseField;

public interface JavaField
{
    Object getValueObject();

    void setValueObject(Object data);

    DatabaseField getDatabaseType();
}
