package net.kigawa.kutil.data.database;

public abstract class DatabaseResolver
{
    public boolean canResolve(FieldInfo fieldInfo)
    {
        // TODO: 2022/08/19
        return false;
    }

    public String resolveTypeName(FieldInfo fieldInfo)
    {
        // TODO: 2022/08/19
        return null;
    }
}
