package net.kigawa.data.mysql;

import net.kigawa.data.database.DatabaseConstraint;
import net.kigawa.data.javaConstraint.JavaConstraint;
import net.kigawa.data.sql.SqlBuilder;

import java.lang.reflect.Field;

public class MysqlConstraint extends DatabaseConstraint
{
    public MysqlConstraint(Field field, JavaConstraint javaConstraint)
    {
        super(field, javaConstraint);
    }

    @Override
    public SqlBuilder getOptions()
    {
        return null;
    }
}
