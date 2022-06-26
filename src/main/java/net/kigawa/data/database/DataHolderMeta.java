package net.kigawa.data.database;

import net.kigawa.data.annotation.DataField;
import net.kigawa.data.annotation.PrimaryKey;
import net.kigawa.data.exception.DatabaseException;
import net.kigawa.data.exception.PrimaryKeyException;
import net.kigawa.data.javatype.JavaDataInterface;
import net.kigawa.kutil.kutil.KutilString;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataHolderMeta<T>
{
    public final Class<T> recordClass;
    public final Constructor<T> constructor;
    public final FieldMeta primaryKey;
    protected final List<FieldMeta> fields = new ArrayList<>();

    protected DataHolderMeta(Class<T> recordClass) throws DatabaseException
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

            if (!JavaDataInterface.class.isAssignableFrom(field.getDeclaringClass()))
                throw new DatabaseException("data field must implement JavaTypeInterface");

            if (!Modifier.isFinal(field.getModifiers())) throw new DatabaseException("data field must be final");

            this.fields.add(new FieldMeta(field));

            if (field.isAnnotationPresent(PrimaryKey.class)) {
                if (primaryKey != null) throw new PrimaryKeyException("only one primary key can be set");
                primaryKey = field;
            }
        }

        if (primaryKey == null) throw new PrimaryKeyException("need a primary key");


        this.recordClass = recordClass;
        this.primaryKey = new FieldMeta(primaryKey);
    }

    public String getName()
    {
        return recordClass.getCanonicalName();
    }
}
