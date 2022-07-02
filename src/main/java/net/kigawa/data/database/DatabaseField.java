package net.kigawa.data.database;


import net.kigawa.data.javatype.JavaField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class DatabaseField
{
    public final String name;
    public final JavaField javaField;
    public final Field field;

    public DatabaseField(String name, JavaField javaField, Field field)
    {
        this.name = name;
        this.javaField = javaField;
        this.field = field;
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotation)
    {
        return field.isAnnotationPresent(annotation);
    }

    public void setValue(Object value)
    {
        javaField.setValueObject(value);
    }

    public abstract String getTypeName();

    public abstract String[] getStrOptions();

    public abstract void writeStatement(PreparedStatement statement, int index);

    public abstract void readResult(ResultSet result);
}
