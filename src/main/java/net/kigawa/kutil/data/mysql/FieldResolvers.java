package net.kigawa.kutil.data.mysql;

import net.kigawa.kutil.data.database.DatabaseField;
import net.kigawa.kutil.data.database.DatabaseResolver;
import net.kigawa.kutil.data.javaField.AbstractDataField;
import net.kigawa.kutil.data.javaField.AbstractNumberField;
import net.kigawa.kutil.data.javaField.IntField;

import java.util.function.Function;

public enum FieldResolvers implements DatabaseResolver.ResolverInterface<AbstractDataField, DatabaseField>
{
    INT(IntField.class, FieldResolvers::getNumberField);

    private static DatabaseField getNumberField(AbstractNumberField numberField)
    {

    }

    private enum NumberField
    {

    }

    private final Class<? extends AbstractDataField> javaFieldClass;
    private final Function<AbstractDataField, DatabaseField> resolver;

    <T extends AbstractDataField> FieldResolvers(Class<T> javaFieldClass, Function<T, DatabaseField> resolver)
    {
        this.javaFieldClass = javaFieldClass;
        this.resolver = (Function<AbstractDataField, DatabaseField>) resolver;
    }

    @Override
    public boolean isInstance(AbstractDataField dataField)
    {
        return javaFieldClass.isInstance(dataField);
    }

    @Override
    public Function<AbstractDataField, DatabaseField> getResolver()
    {
        return resolver;
    }
}