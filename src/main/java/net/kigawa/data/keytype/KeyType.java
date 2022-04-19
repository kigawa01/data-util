package net.kigawa.data.keytype;

/**
 * @deprecated
 */
public abstract class KeyType
{
    public abstract boolean equals(String name);

    public abstract String getSql();
}
