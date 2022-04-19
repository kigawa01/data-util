package net.kigawa.data.keytype;

/**
 * @deprecated
 */
public class UniqueKey extends KeyType
{
    @Override
    public boolean equals(String name)
    {
        return getSql().equals(name);
    }

    @Override
    public String getSql()
    {
        return "UNIQUE";
    }
}
