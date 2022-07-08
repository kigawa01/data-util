package net.kigawa.kutil.data.mysqlfield;

import net.kigawa.kutil.data.javaField.AbstractDataField;
import net.kigawa.kutil.data.sql.SqlBuilder;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TinyInt extends MysqlField
{
    public TinyInt(AbstractDataField dataField, Field field)
    {
        super(dataField, field);
    }

    @Override
    public String getTypeName()
    {
        return null;
    }

    @Override
    public void writeStatement(PreparedStatement statement, int index)
    {

    }

    @Override
    public void readResult(ResultSet result)
    {

    }

    @Override
    protected void configureOptions(SqlBuilder sqlBuilder)
    {

    }
}
