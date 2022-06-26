package net.kigawa.data.database;

import java.lang.reflect.Field;

public class FieldMeta
{
    protected final Field field;

    public FieldMeta(Field field)
    {
        this.field = field;
    }
}
