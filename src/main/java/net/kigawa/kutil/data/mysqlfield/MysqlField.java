package net.kigawa.kutil.data.mysqlfield;

import net.kigawa.kutil.data.annotation.NotNull;
import net.kigawa.kutil.data.annotation.PrimaryKey;
import net.kigawa.kutil.data.annotation.UniqueKey;
import net.kigawa.kutil.data.database.DatabaseField;
import net.kigawa.kutil.data.javaField.AbstractDataField;
import net.kigawa.kutil.data.sql.SqlBuilder;

import java.lang.reflect.Field;

public abstract class MysqlField extends DatabaseField
{
    public MysqlField(AbstractDataField dataField, Field field)
    {
        super(dataField, field);
    }

    protected abstract void configureOptions(SqlBuilder sqlBuilder);

    @Override
    public SqlBuilder getOptions()
    {
        var sql = new SqlBuilder();

        if (hasAnnotation(PrimaryKey.class)) sql.add("PRIMARY KEY");
        if (hasAnnotation(UniqueKey.class)) sql.add("UNIQUE");
        if (hasAnnotation(NotNull.class)) sql.add("NOT NULL");

        configureOptions(sql);

        return sql;
    }
}
