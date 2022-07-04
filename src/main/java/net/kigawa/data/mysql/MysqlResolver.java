package net.kigawa.data.mysql;

import net.kigawa.data.database.DatabaseConstraint;
import net.kigawa.data.database.DatabaseField;
import net.kigawa.data.database.DatabaseResolver;
import net.kigawa.data.exception.DatabaseException;
import net.kigawa.data.javaConstraint.JavaConstraint;
import net.kigawa.data.javaField.IntField;
import net.kigawa.data.javaField.DataField;

import java.util.function.Function;

public class MysqlResolver extends DatabaseResolver
{

    @Override
    protected ResolverInterface<DataField, DatabaseField>[] getFieldResolvers()
    {
        return FieldResolver.values();
    }

    @Override
    protected ResolverInterface<JavaConstraint, DatabaseConstraint>[] getConstraintResolvers()
    {
        return ConstraintResolver.values();
    }
}


enum FieldResolver implements DatabaseResolver.ResolverInterface<DataField, DatabaseField>
{
    INT(IntField.class, intField -> {
        return null;
    });

    private final Class<? extends DataField> javaFieldClass;
    private final Function<DataField, DatabaseField> resolver;

    <T extends DataField> FieldResolver(Class<T> javaFieldClass, Function<T, DatabaseField> resolver)
    {
        this.javaFieldClass = javaFieldClass;
        this.resolver = (Function<DataField, DatabaseField>) resolver;
    }

    @Override
    public boolean isInstance(DataField dataField)
    {
        return javaFieldClass.isInstance(dataField);
    }

    @Override
    public DatabaseField resolve(DataField dataField)
    {
        if (isInstance(dataField)) return resolver.apply(dataField);
        throw new DatabaseException("resolve invalid type");
    }
}

enum ConstraintResolver implements DatabaseResolver.ResolverInterface<JavaConstraint, DatabaseConstraint>
{
    ;

    @Override
    public boolean isInstance(JavaConstraint javaConstraint)
    {
        return false;
    }

    @Override
    public DatabaseConstraint resolve(JavaConstraint javaConstraint)
    {
        return null;
    }
}