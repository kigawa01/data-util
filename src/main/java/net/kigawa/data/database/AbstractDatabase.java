package net.kigawa.data.database;

import net.kigawa.data.exception.DatabaseException;
import net.kigawa.data.function.ThrowSupplier;
import net.kigawa.kutil.kutil.function.ThrowRunnable;
import net.kigawa.kutil.kutil.interfaces.Module;

import java.util.List;

public abstract class AbstractDatabase implements Module
{
    private int connections = 0;


    public void exec(ThrowRunnable runnable)
    {
        connect();
        try {
            runnable.run();
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
        close();
    }

    public <T> T exec(ThrowSupplier<T> supplier)
    {
        T result;
        connect();
        try {
            result = supplier.get();
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
        close();
        return result;
    }

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
        createTable(new DataHolderMeta<T>(recordClass));
    }

    protected abstract <T> void createTable(DataHolderMeta<T> dataHolderMeta);

    public <T> void deleteTable(Class<T> recordClass)
    {
        deleteTable(new DataHolderMeta<T>(recordClass));
    }

    protected abstract <T> void deleteTable(DataHolderMeta<T> dataHolderMeta);

    public <T> T load(Class<T> recordClass, Object keyValue)
    {
        return load(new DataHolderMeta<T>(recordClass), keyValue);
    }

    protected abstract <T> T load(DataHolderMeta<T> recordClass, Object keyValue);

    public <T> void save(T record)
    {
        save(new DataHolderMeta(record.getClass()), record);
    }

    protected abstract <T> void save(DataHolderMeta<T> dataHolderMeta, T dataHolder);

    public <T> List<T> loadFrom(Class<T> recordClass, Field... keys)
    {
        return loadFrom(new DataHolderMeta<T>(recordClass), keys);
    }

    protected abstract <T> List<T> loadFrom(DataHolderMeta<T> dataHolderMeta, Field... keys);

    public <T> void delete(Class<T> recordClass, Object keyValue)
    {
        delete(new DataHolderMeta<T>(recordClass), keyValue);
    }

    protected abstract <T> void delete(DataHolderMeta<T> dataHolderMeta, Object keyValue);
}
