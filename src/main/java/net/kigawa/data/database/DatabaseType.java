package net.kigawa.data.database;

import java.sql.SQLType;

public interface DatabaseType
{
    String typeDefinition();

    SQLType getSQLType();
}
