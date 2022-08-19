package net.kigawa.kutil.data.database;

import net.kigawa.kutil.data.exception.DatabaseException;
import net.kigawa.kutil.data.sql.SqlBuilder;
import net.kigawa.kutil.data.sql.SqlValue;
import net.kigawa.kutil.kutil.interfaces.Module;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public abstract class AbstractDatabase implements Module
{
    private int connections = 0;
    private Set<DatabaseResolver> resolvers = new HashSet<>();

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
        this.resolvers.add(resolver);
    }

    public void removeResolver(DatabaseResolver resolver)
    {
        this.resolvers.remove(resolver);
    }

    public String resolveTypeName(FieldInfo fieldInfo)
    {
        return resolve(fieldInfo, (resolver) -> resolver.resolveTypeName(fieldInfo));
    }

    public SqlValue resolveSqlValue(FieldInfo fieldInfo)
    {
        for (var resolver : resolvers) {
            if (!resolver.canResolve(fieldInfo)) continue;
            return resolver.
        }

        throw new DatabaseException("can not resolve field: " + fieldInfo.toString());
    }

    private <T> T resolve(FieldInfo fieldInfo, Function<DatabaseResolver, T> resolve)
    {
        for (var resolver : resolvers) {
            if (!resolver.canResolve(fieldInfo)) continue;
            return resolve.apply(resolver);
        }

        throw new DatabaseException("can not resolve field: " + fieldInfo.toString());
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
