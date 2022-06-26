package net.kigawa.data.mysql;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * @deprecated
 */
public enum JavaType
{
    STRING(
            String.class,
            "0",
            "255",
            MySQLTypeField.BIT,
            MySQLTypeField.CHAR,
            MySQLTypeField.VARCHAR,
            MySQLTypeField.TINYTEXT,
            MySQLTypeField.TEXT,
            MySQLTypeField.MEDIUMTEXT,
            MySQLTypeField.LONGTEXT
    ),
    BIG_DECIMAL(
            BigDecimal.class,
            null,
            null,
            MySQLTypeField.DECIMAL
    ),
    BOOLEAN(
            Boolean.class,
            "0",
            "1",
            MySQLTypeField.BIT
    ),
    BYTE(
            Byte.class,
            Byte.toString(Byte.MIN_VALUE),
            Byte.toString(Byte.MAX_VALUE),
            MySQLTypeField.BIT,
            MySQLTypeField.TINYINT
    ),
    SHORT(
            Short.class,
            Short.toString(Short.MIN_VALUE),
            Short.toString(Short.MIN_VALUE),
            MySQLTypeField.BIT,
            MySQLTypeField.TINYINT,
            MySQLTypeField.TINYINT_UNSIGNED,
            MySQLTypeField.SMALLINT
    ),
    INTEGER(
            Integer.class,
            Integer.toString(Integer.MIN_VALUE),
            Integer.toString(Integer.MAX_VALUE),
            MySQLTypeField.BIT,
            MySQLTypeField.TINYINT,
            MySQLTypeField.TINYINT_UNSIGNED,
            MySQLTypeField.SMALLINT,
            MySQLTypeField.SMALLINT_UNSIGNED,
            MySQLTypeField.MEDIUMINT,
            MySQLTypeField.MEDIUMINT_UNSIGNED,
            MySQLTypeField.INT
    ),
    LONG(
            Long.class,
            Long.toString(Long.MIN_VALUE),
            Long.toString(Long.MAX_VALUE),
            MySQLTypeField.BIT,
            MySQLTypeField.TINYINT,
            MySQLTypeField.TINYINT_UNSIGNED,
            MySQLTypeField.SMALLINT,
            MySQLTypeField.SMALLINT_UNSIGNED,
            MySQLTypeField.MEDIUMINT,
            MySQLTypeField.MEDIUMINT_UNSIGNED,
            MySQLTypeField.INT,
            MySQLTypeField.INT_UNSIGNED,
            MySQLTypeField.BIGINT
    ),
    BIG_INTEGER(
            BigInteger.class,
            "0",
            "18446744073709551615",
            MySQLTypeField.BIGINT_UNSIGNED
    ),
    DATE(Date.class,
            null,
            null
    ),
    TIME(
            Time.class,
            null,
            null
    ),
    TIMESTAMP(
            Timestamp.class,
            null,
            null
    ),
    ;

    public final Class<?> type;
    public final BigInteger defaultMaxLength;
    public final BigInteger defaultMinLength;
    public final MySQLTypeField[] mysqlTypes;

    JavaType(Class<?> type, String defaultMinLength, String defaultMaxLength, MySQLTypeField... mysqlTypes)
    {
        this.type = type;
        if (defaultMaxLength == null) this.defaultMaxLength = null;
        else this.defaultMaxLength = new BigInteger(defaultMaxLength);
        if (defaultMinLength == null) this.defaultMinLength = null;
        else this.defaultMinLength = new BigInteger(defaultMinLength);
        this.mysqlTypes = mysqlTypes;
    }
}
