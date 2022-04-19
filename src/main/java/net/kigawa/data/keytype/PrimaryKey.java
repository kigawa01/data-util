package net.kigawa.data.keytype;

/**
 * @deprecated
 */
public class PrimaryKey extends KeyType
{
    public static String NAME = "PRIMARY KEY";


    @Override
    public boolean equals(String name)
    {
        return NAME.equals(name);
    }

    @Override
    public String getSql()
    {
        return NAME;
    }
}
