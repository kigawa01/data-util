package net.kigawa.data.database;

import net.kigawa.data.exception.DatabaseException;
import net.kigawa.data.exception.EmptyKeyException;
import net.kigawa.data.function.ThrowSupplier;
import net.kigawa.kutil.kutil.function.ThrowRunnable;
import net.kigawa.kutil.kutil.interfaces.Module;

import java.util.List;
import java.util.function.Supplier;

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

    public void createTable(Class<?> cla)
    {
        createTable(new DataHolderMeta(cla));
    }

    protected abstract void createTable(DataHolderMeta dataHolderMeta);

    public void deleteTable(Class<?> cla)
    {
        deleteTable(new DataHolderMeta(cla));
    }

    protected abstract void deleteTable(DataHolderMeta dataHolderMeta);

    public <T> void load(T dataHolder)
    {
        load(new DataHolderMeta(dataHolder.getClass()), dataHolder);
    }

    protected abstract void load(DataHolderMeta dataHolderMeta, Object dataHolder);

    public <T> void save(T dataHolder)
    {
        save(new DataHolderMeta(dataHolder.getClass()), dataHolder);
    }

    protected abstract void save(DataHolderMeta dataHolderMeta, Object dataHolder);

    public <T> void loadFrom(List<T> dataHolder, String... keys)
    {
        if (dataHolder.isEmpty()) throw new EmptyKeyException("data holder must not empty");
        loadFrom(new DataHolderMeta(dataHolder.get(0).getClass()), dataHolder, keys);
    }

    protected abstract void loadFrom(DataHolderMeta dataHolderMeta, List<?> dataHolder, String... keys);

    public <T> void delete(T dataHolder)
    {
        delete(new DataHolderMeta(dataHolder.getClass()), dataHolder);
    }

    protected abstract void delete(DataHolderMeta dataHolderMeta, Object dataHolder);
}
