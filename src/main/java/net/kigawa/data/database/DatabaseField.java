package net.kigawa.data.database;


import net.kigawa.data.javaField.JavaField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class DatabaseField extends ReflectionContainer
{
    public final JavaField javaField;

    public DatabaseField( JavaField javaField, Field field)
    {
        super(field);
        this.javaField = javaField;
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
