package net.kigawa.data.database;

import net.kigawa.data.annotation.DataField;
import net.kigawa.data.annotation.PrimaryKey;
import net.kigawa.data.exception.DatabaseException;
import net.kigawa.data.exception.PrimaryKeyException;
import net.kigawa.data.javatype.JavaField;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TableMeta<T> implements Iterable<FieldMeta<T>>
{
    public final Class<T> recordClass;
    public final FieldMeta<T> primaryKey;
    protected final Constructor<T> constructor;
    protected final List<FieldMeta<T>> fields = new ArrayList<>();

    protected TableMeta(Class<T> recordClass) throws DatabaseException
    {
        var fields = recordClass.getDeclaredFields();
        Field primaryKey = null;

        try {
            constructor = recordClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new DatabaseException("need non arg constructor");
        }

        for (Field field : fields) {
            if (field.getAnnotation(DataField.class) == null) continue;

            if (!JavaField.class.isAssignableFrom(field.getDeclaringClass()))
                throw new DatabaseException("data field must implement JavaTypeInterface");

            if (!Modifier.isFinal(field.getModifiers())) throw new DatabaseException("data field must be final");

            this.fields.add(new FieldMeta(this, field));

            if (field.isAnnotationPresent(PrimaryKey.class)) {
                if (primaryKey != null) throw new PrimaryKeyException("only one primary key can be set");
                primaryKey = field;
            }
        }

        if (primaryKey == null) throw new PrimaryKeyException("need a primary key");


        this.recordClass = recordClass;
        this.primaryKey = new FieldMeta(this, primaryKey);
    }

    public T getEmptyRecord()
    {
        try {
            return constructor.newInstance();
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new DatabaseException(e);
        }
    }

    public String getName()
    {
        return recordClass.getCanonicalName();
    }

    @Override
    public Iterator<FieldMeta<T>> iterator()
    {
        return fields.listIterator();
    }
}
