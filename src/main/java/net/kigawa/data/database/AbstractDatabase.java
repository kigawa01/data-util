package net.kigawa.data.database;

import net.kigawa.data.exception.DatabaseException;
import net.kigawa.data.javatype.JavaField;
import net.kigawa.data.util.TogetherTwo;
import net.kigawa.kutil.kutil.interfaces.Module;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractDatabase implements Module
{
    private int connections = 0;
    private Set<FieldResolverInterface> fieldResolvers = new HashSet<>();

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

    public void addResolver(FieldResolverInterface resolver)
    {
        fieldResolvers.add(resolver);
    }

    public void removeResolver(FieldResolverInterface resolver)
    {
        fieldResolvers.remove(resolver);
    }

    public DatabaseField resolveField(JavaField javaField)
    {
        for (var resolver : fieldResolvers) {
            if (!resolver.canResolve(javaField)) continue;
            return resolver.resolveType(javaField);
        }

        throw new DatabaseException("can not resolve field: " + javaField.toString());
    }

    public JavaField resolveField(DatabaseField databaseField)
    {
        for (var resolver : fieldResolvers) {
            if (!resolver.canResolve(databaseField)) continue;
            return resolver.resolveType(databaseField);
        }
        throw new DatabaseException("can not resolve field: " + databaseField.toString());
    }

    protected abstract void removeConnection();

    public <T> void createTable(Class<T> recordClass)
    {
        createTable(new TableMeta<T>(recordClass));
    }

    protected abstract <T> void createTable(TableMeta<T> tableMeta);

    public <T> void deleteTable(Class<T> recordClass)
    {
        deleteTable(new TableMeta<T>(recordClass));
    }

    protected abstract <T> void deleteTable(TableMeta<T> tableMeta);

    public <T> T load(Class<T> recordClass, Object keyValue)
    {
        return load(new TableMeta<T>(recordClass), keyValue);
    }

    protected abstract <T> T load(TableMeta<T> recordClass, Object keyValue);

    public <T> void save(T record)
    {
        save(new TableMeta(record.getClass()), record);
    }

    protected abstract <T> void save(TableMeta<T> tableMeta, T dataHolder);

    public <T> List<T> loadFrom(Class<T> recordClass, TogetherTwo... keys)
    {
        return loadFrom(new TableMeta<T>(recordClass), keys);
    }

    protected abstract <T> List<T> loadFrom(TableMeta<T> tableMeta, TogetherTwo... keys);

    public <T> void delete(Class<T> recordClass, Object keyValue)
    {
        delete(new TableMeta<T>(recordClass), keyValue);
    }

    protected abstract <T> void delete(TableMeta<T> tableMeta, Object keyValue);
}
