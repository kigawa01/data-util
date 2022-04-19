package net.kigawa.data.database;

import net.kigawa.kutil.kutil.Kutil;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class DataHolderMeta
{
    protected Field[] fields;
    protected Field primaryKey;

    protected DataHolderMeta(Object dataHolder) throws PrimaryKeyException
    {
        var cla = dataHolder.getClass();
        var fields = cla.getDeclaredFields();
        var list = new LinkedList<Field>();

        for (Field field : fields) {
            if (field.getAnnotation(DataField.class) == null) continue;
            list.add(field);
            if (field.getAnnotation(PrimaryKey.class) == null) continue;
            if (primaryKey != null) throw new PrimaryKeyException("only one primary key can be set");
            primaryKey = field;
        }

        if (primaryKey == null) throw new PrimaryKeyException("need a primary key");


        this.fields = Kutil.getArrangement(list, Field[]::new);
    }
}
