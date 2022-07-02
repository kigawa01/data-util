package net.kigawa.data.mysql;

import net.kigawa.data.annotation.NotNull;
import net.kigawa.data.annotation.PrimaryKey;
import net.kigawa.data.annotation.UniqueKey;
import net.kigawa.data.database.DatabaseField;
import net.kigawa.data.javaField.JavaField;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public abstract class MysqlField extends DatabaseField
{
    public MysqlField(String name, JavaField javaField, Field field)
    {
        super(name, javaField, field);
    }

    protected abstract void configureStrOptions(List<String> options);

    @Override
    public String[] getStrOptions()
    {
        var options = new LinkedList<String>();

        if (hasAnnotation(PrimaryKey.class)) options.add("PRIMARY KEY");
        if (hasAnnotation(UniqueKey.class)) options.add("UNIQUE");
        if (hasAnnotation(NotNull.class)) options.add("NOT NULL");

        configureStrOptions(options);

        return options.toArray(String[]::new);
    }
}
