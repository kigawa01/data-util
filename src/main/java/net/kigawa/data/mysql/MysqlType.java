package net.kigawa.data.mysql;

import net.kigawa.data.database.DatabaseType;

/**
 * @deprecated
 */
public class MysqlType extends DatabaseType
{
    @Override
    public String getName()
    {
        return "mysql";
    }
}
