package net.kigawa.data.database;


import java.sql.PreparedStatement;

public interface DatabaseField
{
    String getStrType();

    String[] getStrOptions();

    void setValue(PreparedStatement statement, int index);
}
