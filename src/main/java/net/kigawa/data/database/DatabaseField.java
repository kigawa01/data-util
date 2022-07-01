package net.kigawa.data.database;


import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    public abstract void setValue(Object value);

    public abstract String getTypeName();

    public abstract String[] getStrOptions();

    public abstract void writeStatement(PreparedStatement statement, int index);

    public abstract void readResult(ResultSet result);
}
