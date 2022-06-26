package net.kigawa.data.database;


import java.sql.PreparedStatement;

public interface DatabaseTypeField
{
    String typeDefinition();

    void setValue(PreparedStatement statement, int index);
}
