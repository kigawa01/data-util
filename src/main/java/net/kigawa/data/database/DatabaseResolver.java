package net.kigawa.data.database;

import net.kigawa.data.exception.DatabaseException;
import net.kigawa.data.javaConstraint.JavaConstraint;
import net.kigawa.data.javaField.DataField;

public abstract class DatabaseResolver
{
    protected <T, R> R resolve(ResolverInterface<T, R>[] resolvers, T t)
    {
        for (var resolver : resolvers) {
            if (!resolver.isInstance(t)) continue;
            return resolver.resolve(t);
        }
        throw new DatabaseException("resolve invalid type");
    }

    protected abstract ResolverInterface<DataField, DatabaseField>[] getFieldResolvers();

    protected abstract ResolverInterface<JavaConstraint, DatabaseConstraint>[] getConstraintResolvers();

    public DatabaseField resolveField(DataField dataField)
    {
        return resolve(getFieldResolvers(), dataField);
    }

    public DatabaseConstraint resolveConstraint(JavaConstraint javaConstraint)
    {
        return resolve(getConstraintResolvers(), javaConstraint);
    }

    public boolean canResolveConstraint(JavaConstraint javaConstraint)
    {
        return canResolve(getConstraintResolvers(), javaConstraint);
    }

    public boolean canResolveField(DataField dataField)
    {
        return canResolve(getFieldResolvers(), dataField);
    }

    public interface ResolverInterface<T, R>
    {
        boolean isInstance(T t);

        ResolverInterface<T, R>[] values();

        R resolve(T t);
    }

    protected <T> boolean canResolve(ResolverInterface<T, ?>[] resolvers, T t)
    {
        for (var resolver : resolvers) {
            if (resolver.isInstance(t)) return true;
        }
        return false;
    }
}
