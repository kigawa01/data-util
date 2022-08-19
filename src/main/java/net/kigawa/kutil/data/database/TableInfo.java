package net.kigawa.kutil.data.database;

import net.kigawa.kutil.data.annotation.DataField;
import net.kigawa.kutil.data.annotation.PrimaryKey;
import net.kigawa.kutil.data.exception.DatabaseException;
import net.kigawa.kutil.data.exception.PrimaryKeyException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TableInfo<T> implements Iterable<FieldInfo>
{
    public final String name;
    public final Class<T> recordClass;
    public final FieldInfo primaryKey;
    public final AbstractDatabase database;
    private final Constructor<T> constructor;
    private final List<FieldInfo> fieldInfos = new ArrayList<>();

    protected TableInfo(Class<T> recordClass, AbstractDatabase database) throws DatabaseException
    {
        var fields = recordClass.getDeclaredFields();
        this.database = database;
        FieldInfo primaryKey = null;

        try {
            constructor = recordClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new DatabaseException("need non arg constructor");
        }

        for (java.lang.reflect.Field field : fields) {
            if (!Modifier.isFinal(field.getModifiers())) throw new DatabaseException("data field must be final");

            if (field.isAnnotationPresent(DataField.class)) {
                var fieldInfo = new FieldInfo(field);
                this.fieldInfos.add(fieldInfo);

                if (field.isAnnotationPresent(PrimaryKey.class)) {
                    if (primaryKey != null) throw new PrimaryKeyException("only one primary key can be set");
                    primaryKey = fieldInfo;
                }
            }
        }

        if (primaryKey == null) throw new PrimaryKeyException("need a primary key");


        this.primaryKey = primaryKey;
        this.recordClass = recordClass;
        this.name = recordClass.getCanonicalName();
    }

    public TableInfo<T> getNewTableInfo()
    {
        return new TableInfo<>(recordClass, database);
    }

    @Override
    public Iterator<FieldInfo> iterator()
    {
        return fieldInfos.listIterator();
    }
}
