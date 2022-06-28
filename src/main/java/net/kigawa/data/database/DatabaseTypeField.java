package net.kigawa.data.database;


import java.sql.PreparedStatement;

public interface DatabaseTypeField
{
    String getStrType();

    String[] getStrOptions();

    void setValue(PreparedStatement statement, int index);
}
