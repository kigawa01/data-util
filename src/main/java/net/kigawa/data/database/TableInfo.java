package net.kigawa.data.database;

import net.kigawa.data.annotation.Constraint;
import net.kigawa.data.annotation.PrimaryKey;
import net.kigawa.data.exception.DatabaseException;
import net.kigawa.data.exception.PrimaryKeyException;
import net.kigawa.data.javaConstraint.JavaConstraint;
import net.kigawa.data.javaField.DataField;

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
    private final List<DatabaseConstraint> constraints = new ArrayList<>();

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
            if (!field.isAnnotationPresent(Constraint.class) && !field.isAnnotationPresent(net.kigawa.data.annotation.DataField.class))
                continue;

            if (!Modifier.isFinal(field.getModifiers())) throw new DatabaseException("data field must be final");


            if (field.isAnnotationPresent(Constraint.class)) {
                Object javaConstraint;
                try {
                    javaConstraint = field.get(record);
                } catch (IllegalAccessException e) {
                    throw new DatabaseException(e);
                }

                if (!(javaConstraint instanceof JavaConstraint))
                    throw new DatabaseException("constraint must extend JavaConstraint");

                var databaseConstraint = database.resolveConstraint((JavaConstraint) javaConstraint);
                constraints.add(databaseConstraint);

            }
            if (field.isAnnotationPresent(net.kigawa.data.annotation.DataField.class)) {
                Object javaField;
                try {
                    javaField = field.get(record);
                } catch (IllegalAccessException e) {
                    throw new DatabaseException(e);
                }

                if (!(javaField instanceof DataField))
                    throw new DatabaseException("field must extend JavaTypeInterface");

                var databaseField = database.resolveField((DataField) javaField);
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

    public List<DatabaseConstraint> getConstraints()
    {
        return new ArrayList<>(constraints);
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
