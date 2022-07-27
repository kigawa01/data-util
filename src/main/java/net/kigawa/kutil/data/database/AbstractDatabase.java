package net.kigawa.kutil.data.database;

import net.kigawa.kutil.data.exception.DatabaseException;
import net.kigawa.kutil.data.javaConstraint.JavaOption;
import net.kigawa.kutil.data.javaField.AbstractDataField;
import net.kigawa.kutil.data.sql.SqlBuilder;
import net.kigawa.kutil.kutil.interfaces.Module;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractDatabase implements Module
{
    private int connections = 0;
    private Set<DatabaseResolver> resolver = new HashSet<>();

    public void connect()
    {
        if (connections == 0) {
            createConnection();
        }
        connections++;
    }

    protected abstract void createConnection();

    public void close()
    {
        if (connections < 0) return;

        connections--;
        if (connections == 0) {
            removeConnection();
        }
    }

    public void addResolver(DatabaseResolver resolver)
    {
        this.resolver.add(resolver);
    }

    public void removeResolver(DatabaseResolver resolver)
    {
        this.resolver.remove(resolver);
    }

    public DatabaseField resolveField(AbstractDataField dataField, Field field)
    {
        for (var resolver : resolver) {
            if (!resolver.canResolveField(dataField)) continue;
            return resolver.resolveField(dataField, field);
        }

        throw new DatabaseException("can not resolve field: " + dataField.toString());
    }

    public DatabaseOption resolveOption(JavaOption javaOption, Field field)
    {
        for (var resolver : resolver) {
            if (!resolver.canResolveConstraint(javaOption)) continue;
            return resolver.resolveConstraint(javaOption, field);
        }

        throw new DatabaseException("can not resolve constraint: " + javaOption.toString());
    }

    protected abstract void removeConnection();

    public <T> void createTable(Class<T> recordClass)
    {
        createTable(new TableInfo<T>(recordClass, this));
    }

    protected abstract <T> void createTable(TableInfo<T> tableMeta);

    public <T> void deleteTable(Class<T> recordClass)
    {
        deleteTable(new TableInfo<T>(recordClass, this));
    }

    protected abstract <T> void deleteTable(TableInfo<T> tableMeta);

    public <T> T load(Class<T> recordClass, Object keyValue)
    {
        return load(new TableInfo<T>(recordClass, this), keyValue);
    }

    protected abstract <T> T load(TableInfo<T> recordClass, Object keyValue);

    public <T> void save(T record)
    {
        save(new TableInfo(record.getClass(), this), record);
    }

    protected abstract <T> void save(TableInfo<T> tableMeta, T dataHolder);

    public <T> List<T> loadWhere(Class<T> recordClass, SqlBuilder where)
    {
        return loadWhere(new TableInfo<T>(recordClass, this), where);
    }

    protected abstract <T> List<T> loadWhere(TableInfo<T> tableMeta, SqlBuilder where);

    public <T> void delete(Class<T> recordClass, Object keyValue)
    {
        delete(new TableInfo<T>(recordClass, this), keyValue);
    }

    protected abstract <T> void delete(TableInfo<T> tableMeta, Object keyValue);
}
