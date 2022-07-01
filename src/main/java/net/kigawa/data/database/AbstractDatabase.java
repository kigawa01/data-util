package net.kigawa.data.database;

import net.kigawa.data.exception.DatabaseException;
import net.kigawa.data.javatype.JavaField;
import net.kigawa.data.sql.SqlBuilder;
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
        createTable(new TableInfo<T>(recordClass,this));
    }

    protected abstract <T> void createTable(TableInfo<T> tableMeta);

    public <T> void deleteTable(Class<T> recordClass)
    {
        deleteTable(new TableInfo<T>(recordClass,this));
    }

    protected abstract <T> void deleteTable(TableInfo<T> tableMeta);

    public <T> T load(Class<T> recordClass, Object keyValue)
    {
        return load(new TableInfo<T>(recordClass,this), keyValue);
    }

    protected abstract <T> T load(TableInfo<T> recordClass, Object keyValue);

    public <T> void save(T record)
    {
        save(new TableInfo(record.getClass(),this), record);
    }

    protected abstract <T> void save(TableInfo<T> tableMeta, T dataHolder);

    public <T> List<T> loadWhere(Class<T> recordClass, SqlBuilder where)
    {
        return loadWhere(new TableInfo<T>(recordClass,this), where);
    }

    protected abstract <T> List<T> loadWhere(TableInfo<T> tableMeta, SqlBuilder where);

    public <T> void delete(Class<T> recordClass, Object keyValue)
    {
        delete(new TableInfo<T>(recordClass,this), keyValue);
    }

    protected abstract <T> void delete(TableInfo<T> tableMeta, Object keyValue);
}
