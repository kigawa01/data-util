package net.kigawa.data.database;

import net.kigawa.kutil.kutil.Kutil;

import java.lang.reflect.Field;
import java.util.LinkedList;

public abstract class AbstractRecord
{
    private Field[] fields;

    public AbstractRecord()
    {
        var cla = getClass();
        var fields = cla.getDeclaredFields();
        var list = new LinkedList<Field>();
        for (Field field : fields) {
            if (field.getAnnotation(DatabaseField.class) == null) continue;
            list.add(field);
        }
        this.fields = Kutil.getArrangement(list, Field[]::new);
    }

    public void load(String[] field)
    {

    }

    public void save(String[] field)
    {

    }
}
