package net.kigawa.data.database;

import net.kigawa.data.javaConstraint.JavaConstraint;

import java.lang.reflect.Field;

public abstract class DatabaseConstraint extends ReflectionContainer
{
    private final JavaConstraint javaConstraint;

    public DatabaseConstraint(Field field, JavaConstraint javaConstraint)
    {
        super(field);
        this.javaConstraint = javaConstraint;
    }

    public String getColumnName()
    {
        return javaConstraint.getColumnName();
    }

    public String getParentName()
    {
        return javaConstraint.getParentName();
    }

    public String getParentColumnName()
    {
        return javaConstraint.getParentColumnName();
    }

}
