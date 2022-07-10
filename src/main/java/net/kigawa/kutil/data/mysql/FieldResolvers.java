package net.kigawa.kutil.data.mysql;

import net.kigawa.kutil.data.database.DatabaseField;
import net.kigawa.kutil.data.database.DatabaseResolver;
import net.kigawa.kutil.data.exception.DatabaseException;
import net.kigawa.kutil.data.javaField.AbstractDataField;
import net.kigawa.kutil.data.javaField.AbstractNumberField;
import net.kigawa.kutil.data.javaField.IntField;
import net.kigawa.kutil.data.mysqlfield.*;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;

public enum FieldResolvers implements DatabaseResolver.ResolverInterface<AbstractDataField, DatabaseField>
{
    INT(IntField.class, FieldResolvers::resolveNumberField),
    ;

    private static DatabaseField resolveNumberField(AbstractNumberField numberField)
    {
        for (NumberFieldRange numberFieldRange : NumberFieldRange.values()) {
            if (numberField.getMin().compareTo(numberFieldRange.min) < 0) continue;
            if (numberField.getMax().compareTo(numberFieldRange.max) > 0) continue;

            return numberFieldRange
        }
        throw new DatabaseException("can not resolve number field range");
    }

    private enum NumberFieldRange
    {
        TINYINT("-128", "127", TinyInt::new),
        UNREGISTERED_TINYINT("0", "255", UnregisteredTinyInt::new),
        SMALL_INT("-32768", "32767", SmallInt::new),
        UNREGISTERED_SMALLINT("0", "65535", UnregisteredSmallInt::new),
        MEDIUMINT("-8388608", "8388607", MediumInt::new),
        UNREGISTERED_MEDIUMINT("0", "16777215", UnregisteredMediumInt::new),
        INT("-2147483648", "2147483647", MysqlInt::new),
        UNREGISTERED_INT("0", "4294967295", UnregisteredInt::new),
        BIGINT("-9223372036854775808", "9223372036854775807", BigInt::new),
        UNREGISTERED_BIGINT("0", "18446744073709551615", UnregisteredBigInt::new),
        ;

        public final BigInteger min;
        public final BigInteger max;
        public final BiConsumer<AbstractDataField, Field> createDatabaseField;

        NumberFieldRange(String min, String max, BiConsumer<AbstractDataField, Field> createDatabaseField)
        {
            this.min = new BigInteger(min);
            this.max = new BigInteger(max);
            this.createDatabaseField = createDatabaseField;
        }

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