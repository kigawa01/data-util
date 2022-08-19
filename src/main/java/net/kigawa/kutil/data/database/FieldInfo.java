package net.kigawa.kutil.data.database;

import java.lang.reflect.Field;

public class FieldInfo
{
    public final String name;
    public final Field field;

    public FieldInfo(Field field)
    {
        this.field = field;
        this.name = field.getName();
    }
}
