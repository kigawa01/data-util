package net.kigawa.kutil.data.database;

import net.kigawa.kutil.data.sql.SqlBuilder;

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
