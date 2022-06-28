package net.kigawa.data.database;

import net.kigawa.data.exception.DatabaseException;

import java.lang.reflect.Field;

public class FieldMeta<T>
{
    private final TableMeta<T> tableMeta;
    protected final Field field;

    protected FieldMeta(TableMeta<T> tableMeta, Field field)
    {
        this.tableMeta = tableMeta;
        this.field = field;
        getEmptyDatabaseTypeField();
    }

    public String getName()
    {
        return field.getName();
    }

    public DatabaseTypeField getEmptyDatabaseTypeField()
    {
        try {
            var record = tableMeta.getEmptyRecord();
            return (DatabaseTypeField) field.get(record);
        } catch (IllegalAccessException e) {
            throw new DatabaseException(e);
        }
    }
}
