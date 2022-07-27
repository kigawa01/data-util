package net.kigawa.kutil.data.database;

import net.kigawa.kutil.data.annotation.Constraint;
import net.kigawa.kutil.data.annotation.DataField;
import net.kigawa.kutil.data.annotation.PrimaryKey;
import net.kigawa.kutil.data.exception.DatabaseException;
import net.kigawa.kutil.data.exception.PrimaryKeyException;
import net.kigawa.kutil.data.javaConstraint.JavaOption;
import net.kigawa.kutil.data.javaField.AbstractDataField;

import java.lang.reflect.Constructor;
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
    private final List<DatabaseField> databaseFields = new ArrayList<>();
    private final List<DatabaseOption> options = new ArrayList<>();

    protected TableInfo(Class<T> recordClass, AbstractDatabase database) throws DatabaseException
    {
        var fields = recordClass.getDeclaredFields();
        this.database = database;
        DatabaseField primaryKey = null;

        try {
            Constructor<T> constructor = recordClass.getConstructor();
            record = constructor.newInstance();
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new DatabaseException("need non arg constructor");
        }

        for (java.lang.reflect.Field field : fields) {
            if (!field.isAnnotationPresent(Constraint.class) && !field.isAnnotationPresent(DataField.class))
                continue;

            if (!Modifier.isFinal(field.getModifiers())) throw new DatabaseException("data field must be final");


            if (field.isAnnotationPresent(Constraint.class)) {
                Object javaConstraint;
                try {
                    javaConstraint = field.get(record);
                } catch (IllegalAccessException e) {
                    throw new DatabaseException(e);
                }

                if (!(javaConstraint instanceof JavaOption))
                    throw new DatabaseException("constraint must extend JavaConstraint");

                var databaseConstraint = database.resolveOption((JavaOption) javaConstraint, field);
                options.add(databaseConstraint);

            }
            if (field.isAnnotationPresent(DataField.class)) {
                Object javaField;
                try {
                    javaField = field.get(record);
                } catch (IllegalAccessException e) {
                    throw new DatabaseException(e);
                }

                if (!(javaField instanceof AbstractDataField))
                    throw new DatabaseException("field must extend JavaTypeInterface");

                var databaseField = database.resolveField((AbstractDataField) javaField, field);
                this.databaseFields.add(databaseField);

                if (field.isAnnotationPresent(PrimaryKey.class)) {
                    if (primaryKey != null) throw new PrimaryKeyException("only one primary key can be set");
                    primaryKey = databaseField;
                }
            }
        }

        if (primaryKey == null) throw new PrimaryKeyException("need a primary key");


        this.primaryKey = primaryKey;
        this.recordClass = recordClass;
        this.name = recordClass.getCanonicalName();
    }

    public List<DatabaseOption> getOptions()
    {
        return new ArrayList<>(options);
    }

    public TableInfo<T> getNewTableInfo()
    {
        return new TableInfo<>(recordClass, database);
    }

    @Override
    public Iterator<DatabaseField> iterator()
    {
        return databaseFields.listIterator();
    }
}
