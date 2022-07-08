package net.kigawa.kutil.data.mysql;

import net.kigawa.kutil.data.database.DatabaseField;
import net.kigawa.kutil.data.database.DatabaseOption;
import net.kigawa.kutil.data.database.DatabaseResolver;
import net.kigawa.kutil.data.javaConstraint.JavaOption;
import net.kigawa.kutil.data.javaField.AbstractDataField;

public class MysqlResolver extends DatabaseResolver
{

    @Override
    protected ResolverInterface<AbstractDataField, DatabaseField>[] getFieldResolvers()
    {
        return FieldResolvers.values();
    }

    @Override
    protected ResolverInterface<JavaOption, DatabaseOption>[] getConstraintResolvers()
    {
        return OptionResolver.values();
    }
}
