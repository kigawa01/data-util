package net.kigawa.data.database;

public abstract class AbstractDatabase
{
    public abstract void createTable(Class<? extends DataHolderMeta> cla);

    public abstract void deleteTable(Class<? extends DataHolderMeta> cla);

    public abstract <T> void load(T dataHolder);

    public abstract <T> void save(T dataHolder);
}
