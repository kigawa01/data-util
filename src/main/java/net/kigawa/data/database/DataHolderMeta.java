package net.kigawa.data.database;

import net.kigawa.kutil.kutil.Kutil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;

public class DataHolderMeta
{
    protected Field[] fields;
    protected Field primaryKey;

    protected DataHolderMeta(Class<?> cla) throws PrimaryKeyException
    {
        var fields = cla.getDeclaredFields();
        var list = new LinkedList<Field>();

        for (Field field : fields) {
            if (field.getAnnotation(DataField.class) == null) continue;
            list.add(field);
            if (Modifier.isFinal(field.getModifiers())) continue;
            if (primaryKey != null) throw new PrimaryKeyException("only one primary(final) key can be set");
            primaryKey = field;
        }

        if (primaryKey == null) throw new PrimaryKeyException("need a primary(final) key");


        this.fields = Kutil.getArrangement(list, Field[]::new);
    }
}
