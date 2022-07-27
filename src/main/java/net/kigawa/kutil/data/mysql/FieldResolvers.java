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
import java.util.function.BiFunction;

public enum FieldResolvers implements DatabaseResolver.ResolverInterface<AbstractDataField, DatabaseField>
{
    INT(IntField.class, FieldResolvers::resolveNumberField),
    ;

    private static DatabaseField resolveNumberField(AbstractNumberField numberField, Field field)
    {
        for (NumberFieldRange numberFieldRange : NumberFieldRange.values()) {
            if (numberField.getMin().compareTo(numberFieldRange.min) < 0) continue;
            if (numberField.getMax().compareTo(numberFieldRange.max) > 0) continue;

            return numberFieldRange.createDatabaseField.apply(numberField, field);
        }
        throw new DatabaseException("can not resolve number field range");
    }


    private final Class<? extends AbstractDataField> javaFieldClass;
    private final BiFunction<AbstractDataField, Field, DatabaseField> resolver;

    <T extends AbstractDataField> FieldResolvers(Class<T> javaFieldClass, BiFunction<T, Field, DatabaseField> resolver)
    {
        this.javaFieldClass = javaFieldClass;
        this.resolver = (BiFunction<AbstractDataField, Field, DatabaseField>) resolver;
    }

    @Override
    public boolean isInstance(AbstractDataField dataField)
    {
        return javaFieldClass.isInstance(dataField);
    }

    @Override
    public BiFunction<AbstractDataField, Field, DatabaseField> getResolver()
    {
        return resolver;
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
        public final BiFunction<AbstractDataField, Field, DatabaseField> createDatabaseField;

        NumberFieldRange(String min, String max, BiFunction<AbstractDataField, Field, DatabaseField> createDatabaseField)
        {
            this.min = new BigInteger(min);
            this.max = new BigInteger(max);
            this.createDatabaseField = createDatabaseField;
        }

    }
}