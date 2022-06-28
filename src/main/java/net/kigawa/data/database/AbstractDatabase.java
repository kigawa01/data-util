package net.kigawa.data.database;

import net.kigawa.data.exception.DatabaseException;
import net.kigawa.data.function.ThrowSupplier;
import net.kigawa.kutil.kutil.function.ThrowRunnable;
import net.kigawa.kutil.kutil.interfaces.Module;

import java.util.List;

public abstract class AbstractDatabase implements Module
{
    private int connections = 0;

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

    public <T> List<T> loadFrom(Class<T> recordClass, Field... keys)
    {
        return loadFrom(new TableMeta<T>(recordClass), keys);
    }

    protected abstract <T> List<T> loadFrom(TableMeta<T> tableMeta, Field... keys);

    public <T> void delete(Class<T> recordClass, Object keyValue)
    {
        delete(new TableMeta<T>(recordClass), keyValue);
    }

    protected abstract <T> void delete(TableMeta<T> tableMeta, Object keyValue);
}
