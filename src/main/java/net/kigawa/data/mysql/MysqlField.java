package net.kigawa.data.mysql;

import net.kigawa.data.annotation.PrimaryKey;
import net.kigawa.data.database.DatabaseField;
import net.kigawa.data.javatype.JavaField;

import java.lang.reflect.Field;
import java.util.LinkedList;

public abstract class MysqlField extends DatabaseField
{
    public MysqlField(String name, JavaField javaField, Field field)
    {
        super(name, javaField, field);
    }

    @Override
    public String[] getStrOptions()
    {
        var list = new LinkedList<String>();

        if (hasAnnotation(PrimaryKey.class)) list.add("PRIMARY KEY");

        return list.toArray(String[]::new);
    }
}
