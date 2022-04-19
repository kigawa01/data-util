package net.kigawa.data.mysql;

import net.kigawa.data.database.SqlDataType;
import net.kigawa.data.javadata.JavaData;
import net.kigawa.data.javadata.StringData;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @deprecated
 */

public class VarcharType extends SqlDataType<StringData>
{
    private final int size;

    public VarcharType(int size)
    {
        this.size = size;
    }

    @Override
    public StringData getData(String key, ResultSet resultSet) throws SQLException
    {
        return new StringData(resultSet.getString(key));
    }

    @Override
    public String getSql()
    {
        return "VARCHAR(" + size + ")";
    }

    @Override
    public boolean isAllow(JavaData javaData)
    {
        return javaData instanceof StringData;
    }

    @Override
    public boolean equals(String name)
    {
        return "VARCHAR".equals(name);
    }
}
