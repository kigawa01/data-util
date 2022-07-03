package net.kigawa.data.mysql;

import net.kigawa.data.annotation.NotNull;
import net.kigawa.data.annotation.PrimaryKey;
import net.kigawa.data.annotation.UniqueKey;
import net.kigawa.data.database.DatabaseField;
import net.kigawa.data.javaField.JavaField;
import net.kigawa.data.sql.SqlBuilder;

import java.lang.reflect.Field;

public abstract class MysqlField extends DatabaseField
{
    public MysqlField(JavaField javaField, Field field)
    {
        super(javaField, field);
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
