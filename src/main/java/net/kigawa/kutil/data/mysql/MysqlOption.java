package net.kigawa.kutil.data.mysql;

import net.kigawa.kutil.data.database.DatabaseOption;
import net.kigawa.kutil.data.javaConstraint.JavaOption;
import net.kigawa.kutil.data.sql.SqlBuilder;

import java.lang.reflect.Field;

public class MysqlOption extends DatabaseOption
{
    public MysqlOption(Field field, JavaOption javaOption)
    {
        super(field, javaOption);
    }

    @Override
    public SqlBuilder getOptions()
    {
        return null;
    }
}
