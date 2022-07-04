package net.kigawa.data.database;


import net.kigawa.data.javaField.DataField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class DatabaseField extends ReflectionContainer
{
    public final DataField dataField;

    public DatabaseField(DataField dataField, Field field)
    {
        super(field);
        this.dataField = dataField;
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotation)
    {
        return field.isAnnotationPresent(annotation);
    }

    public void setValue(Object value)
    {
        dataField.setValueObject(value);
    }

    public abstract String getTypeName();


    public abstract void writeStatement(PreparedStatement statement, int index);

    public abstract void readResult(ResultSet result);
}
