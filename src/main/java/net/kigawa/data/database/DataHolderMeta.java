package net.kigawa.data.database;

import net.kigawa.data.exception.PrimaryKeyException;
import net.kigawa.kutil.kutil.Kutil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;

public class DataHolderMeta
{
    public final Class<?> cla;
    public final Field primaryKey;
    protected Field[] fields;

    protected DataHolderMeta(Class<?> cla) throws PrimaryKeyException
    {
        var fields = cla.getDeclaredFields();
        var list = new LinkedList<Field>();
        Field primaryKey = null;

        for (Field field : fields) {
            if (field.getAnnotation(DataField.class) == null) continue;
            list.add(field);
            if (Modifier.isFinal(field.getModifiers())) continue;
            if (primaryKey != null) throw new PrimaryKeyException("only one primary(final) key can be set");
            primaryKey = field;
        }

        if (primaryKey == null) throw new PrimaryKeyException("need a primary(final) key");


        this.cla = cla;
        this.primaryKey = primaryKey;
        this.fields = Kutil.getArrangement(list, Field[]::new);
    }

    public String getName()
    {
        return cla.getCanonicalName();
    }
}
