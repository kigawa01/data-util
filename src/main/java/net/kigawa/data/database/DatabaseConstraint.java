package net.kigawa.data.database;

import java.lang.reflect.Field;

public class DatabaseConstraint extends ReflectionContainer
{
    public DatabaseConstraint(Field field)
    {
        super(field);
    }
}
