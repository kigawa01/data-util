package net.kigawa.data.database;

import net.kigawa.kutil.kutil.interfaces.Module;

import java.util.Map;

public abstract class AbstractDatabase implements Module
{
    public void createTable(Class<?> cla) throws PrimaryKeyException
    {
        createTable(new DataHolderMeta(cla));
    }

    protected abstract void createTable(DataHolderMeta dataHolderMeta);

    public void deleteTable(Class<?> cla) throws PrimaryKeyException
    {
        deleteTable(new DataHolderMeta(cla));
    }

    protected abstract void deleteTable(DataHolderMeta dataHolderMeta);

    public <T> void load(T dataHolder) throws PrimaryKeyException
    {
        load(new DataHolderMeta(dataHolder.getClass()), dataHolder);
    }

    protected abstract void load(DataHolderMeta dataHolderMeta, Object dataHolder);

    public <T> void save(T dataHolder) throws PrimaryKeyException
    {
        save(new DataHolderMeta(dataHolder.getClass()), dataHolder);
    }

    protected abstract void save(DataHolderMeta dataHolderMeta, Object dataHolder);

    public <T> void loadFrom(T dataHolder, String... keys) throws PrimaryKeyException
    {
        loadFrom(new DataHolderMeta(dataHolder.getClass()), dataHolder, keys);
    }

    protected abstract void loadFrom(DataHolderMeta dataHolderMeta, Object dataHolder, String... keys);

    public <T> void delete(T dataHolder) throws PrimaryKeyException
    {
        delete(new DataHolderMeta(dataHolder.getClass()), dataHolder);
    }

    protected abstract void delete(DataHolderMeta dataHolderMeta, Object dataHolder);
}
