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

public class TableInfo<T> implements Iterable<DatabaseField>
{
    public final String name;
    public final Class<T> recordClass;
    public final DatabaseField primaryKey;
    public final T record;
    public final AbstractDatabase database;
    protected final Constructor<T> constructor;
    protected final List<DatabaseField> fields = new ArrayList<>();

    protected TableInfo(Class<T> recordClass, AbstractDatabase database) throws DatabaseException
    {
        var fields = recordClass.getDeclaredFields();
        this.database = database;
        DatabaseField primaryKey = null;

        try {
            constructor = recordClass.getConstructor();
            record = constructor.newInstance();
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new DatabaseException("need non arg constructor");
        }

        for (Field field : fields) {
            if (field.getAnnotation(DataField.class) == null) continue;

            Object javaField;
            try {
                javaField = field.get(record);
            } catch (IllegalAccessException e) {
                throw new DatabaseException(e);
            }

            if (!(javaField instanceof JavaField))
                throw new DatabaseException("data field must implement JavaTypeInterface");

            if (!Modifier.isFinal(field.getModifiers())) throw new DatabaseException("data field must be final");

            var databaseField = database.resolveField((JavaField) javaField);
            this.fields.add(databaseField);

            if (field.isAnnotationPresent(PrimaryKey.class)) {
                if (primaryKey != null) throw new PrimaryKeyException("only one primary key can be set");
                primaryKey = databaseField;
            }
        }

        if (primaryKey == null) throw new PrimaryKeyException("need a primary key");


        this.primaryKey = primaryKey;
        this.recordClass = recordClass;
        this.name = recordClass.getCanonicalName();
    }

    @Override
    public Iterator<DatabaseField> iterator()
    {
        return fields.listIterator();
    }
}
