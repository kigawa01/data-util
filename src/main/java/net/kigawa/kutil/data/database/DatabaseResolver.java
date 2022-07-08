package net.kigawa.kutil.data.database;

import net.kigawa.kutil.data.exception.DatabaseException;
import net.kigawa.kutil.data.javaConstraint.JavaOption;
import net.kigawa.kutil.data.javaField.AbstractDataField;

import java.util.function.Function;

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

    protected abstract ResolverInterface<AbstractDataField, DatabaseField>[] getFieldResolvers();

    protected abstract ResolverInterface<JavaOption, DatabaseOption>[] getConstraintResolvers();

    public DatabaseField resolveField(AbstractDataField dataField)
    {
        return resolve(getFieldResolvers(), dataField);
    }

    public DatabaseOption resolveConstraint(JavaOption javaOption)
    {
        return resolve(getConstraintResolvers(), javaOption);
    }

    public boolean canResolveConstraint(JavaOption javaOption)
    {
        return canResolve(getConstraintResolvers(), javaOption);
    }

    public boolean canResolveField(AbstractDataField dataField)
    {
        return canResolve(getFieldResolvers(), dataField);
    }

    public interface ResolverInterface<T, R>
    {
        boolean isInstance(T t);

        ResolverInterface<T, R>[] values();

        Function<T, R> getResolver();

        default R resolve(T dataField)
        {
            if (isInstance(dataField)) return getResolver().apply(dataField);
            throw new DatabaseException("resolve invalid type");
        }
    }

    protected <T> boolean canResolve(ResolverInterface<T, ?>[] resolvers, T t)
    {
        for (var resolver : resolvers) {
            if (resolver.isInstance(t)) return true;
        }
        return false;
    }
}
