package net.kigawa.data.database;

import net.kigawa.data.sql.SqlBuilder;

import java.lang.reflect.Field;

public abstract class ReflectionContainer
{
    public final String name;
    public final Field field;

    protected ReflectionContainer(Field field)
    {
        this.field = field;
        name = field.getName();
    }

    public abstract SqlBuilder getOptions();
}
