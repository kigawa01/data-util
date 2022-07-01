package net.kigawa.data.database;


import java.sql.PreparedStatement;

public abstract class DatabaseField
{
    private final String name;

    public DatabaseField(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public abstract String getTypeName();

    public abstract String[] getStrOptions();

    public abstract void setValueToStatement(PreparedStatement statement, int index);
}
