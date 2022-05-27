package net.kigawa.data.database;

import net.kigawa.data.annotation.DataField;
import net.kigawa.data.annotation.PrimaryKey;
import net.kigawa.data.exception.DatabaseException;
import net.kigawa.data.exception.PrimaryKeyException;
import net.kigawa.data.javatype.JavaDataInterface;
import net.kigawa.kutil.kutil.Kutil;
import net.kigawa.kutil.kutil.KutilString;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

public class DataHolderMeta
{
    public final Class<?> cla;
    public final Constructor<?> constructor;
    public final Field primaryKey;
    protected Field[] fields;

    protected DataHolderMeta(Class<?> cla) throws DatabaseException
    {
        var fields = cla.getDeclaredFields();
        var list = new LinkedList<Field>();
        Field primaryKey = null;

        try {
            constructor = cla.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new DatabaseException("need non arg constructor");
        }

        for (Field field : fields) {
            if (field.getAnnotation(DataField.class) == null) continue;

            if (!JavaDataInterface.class.isAssignableFrom(field.getDeclaringClass()))
                throw new DatabaseException("data field must implement JavaTypeInterface");

            if (!Modifier.isFinal(field.getModifiers())) throw new DatabaseException("data field must be final");

            list.add(field);

            if (field.isAnnotationPresent(PrimaryKey.class)) {
                if (primaryKey != null) throw new PrimaryKeyException("only one primary key can be set");
                primaryKey = field;
            }
        }

        if (primaryKey == null) throw new PrimaryKeyException("need a primary key");


        this.cla = cla;
        this.primaryKey = primaryKey;
        this.fields = Kutil.getArrangement(list, Field[]::new);
    }

    public String columnDefinitions(TypeResolverInterface typeResolver)
    {
        var sb = new StringBuffer();

        try {
            Object obj = constructor.newInstance();
            List<String> list = new LinkedList<>();

            for (Field field : fields) {
                var javaData = (JavaDataInterface) field.get(obj);
                var databaseType = typeResolver.resolveType(javaData);

                list.add(databaseType.typeDefinition());
            }

            KutilString.insertSymbol(sb, " ", list);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new DatabaseException(e);
        }

        return sb.toString();
    }

    public String getName()
    {
        return cla.getCanonicalName();
    }
}
