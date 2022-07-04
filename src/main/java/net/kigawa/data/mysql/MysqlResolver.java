package net.kigawa.data.mysql;

import net.kigawa.data.database.DatabaseConstraint;
import net.kigawa.data.database.DatabaseField;
import net.kigawa.data.database.DatabaseResolverInterface;
import net.kigawa.data.javaConstraint.JavaConstraint;
import net.kigawa.data.javaField.JavaField;

public class MysqlResolver implements DatabaseResolverInterface
{
    @Override
    public DatabaseField resolveField(JavaField javaField)
    {
        return null;
    }

    @Override
    public DatabaseConstraint resolveConstraint(JavaConstraint javaConstraint)
    {
        return null;
    }

    @Override
    public boolean canResolveConstraint(JavaConstraint javaConstraint)
    {
        return false;
    }

    @Override
    public boolean canResolveField(JavaField javaField)
    {
        return false;
    }
}
