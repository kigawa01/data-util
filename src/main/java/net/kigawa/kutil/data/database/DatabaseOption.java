package net.kigawa.kutil.data.database;

import net.kigawa.kutil.data.javaConstraint.JavaOption;

import java.lang.reflect.Field;

public abstract class DatabaseOption extends ReflectionContainer
{
    private final JavaOption javaOption;

    public DatabaseOption(Field field, JavaOption javaOption)
    {
        super(field);
        this.javaOption = javaOption;
    }

    public String getColumnName()
    {
        return javaOption.getColumnName();
    }

    public String getParentName()
    {
        return javaOption.getParentName();
    }

    public String getParentColumnName()
    {
        return javaOption.getParentColumnName();
    }

}
