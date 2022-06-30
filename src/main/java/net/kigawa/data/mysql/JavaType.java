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
            MySQLField.BIT,
            MySQLField.CHAR,
            MySQLField.VARCHAR,
            MySQLField.TINYTEXT,
            MySQLField.TEXT,
            MySQLField.MEDIUMTEXT,
            MySQLField.LONGTEXT
    ),
    BIG_DECIMAL(
            BigDecimal.class,
            null,
            null,
            MySQLField.DECIMAL
    ),
    BOOLEAN(
            Boolean.class,
            "0",
            "1",
            MySQLField.BIT
    ),
    BYTE(
            Byte.class,
            Byte.toString(Byte.MIN_VALUE),
            Byte.toString(Byte.MAX_VALUE),
            MySQLField.BIT,
            MySQLField.TINYINT
    ),
    SHORT(
            Short.class,
            Short.toString(Short.MIN_VALUE),
            Short.toString(Short.MIN_VALUE),
            MySQLField.BIT,
            MySQLField.TINYINT,
            MySQLField.TINYINT_UNSIGNED,
            MySQLField.SMALLINT
    ),
    INTEGER(
            Integer.class,
            Integer.toString(Integer.MIN_VALUE),
            Integer.toString(Integer.MAX_VALUE),
            MySQLField.BIT,
            MySQLField.TINYINT,
            MySQLField.TINYINT_UNSIGNED,
            MySQLField.SMALLINT,
            MySQLField.SMALLINT_UNSIGNED,
            MySQLField.MEDIUMINT,
            MySQLField.MEDIUMINT_UNSIGNED,
            MySQLField.INT
    ),
    LONG(
            Long.class,
            Long.toString(Long.MIN_VALUE),
            Long.toString(Long.MAX_VALUE),
            MySQLField.BIT,
            MySQLField.TINYINT,
            MySQLField.TINYINT_UNSIGNED,
            MySQLField.SMALLINT,
            MySQLField.SMALLINT_UNSIGNED,
            MySQLField.MEDIUMINT,
            MySQLField.MEDIUMINT_UNSIGNED,
            MySQLField.INT,
            MySQLField.INT_UNSIGNED,
            MySQLField.BIGINT
    ),
    BIG_INTEGER(
            BigInteger.class,
            "0",
            "18446744073709551615",
            MySQLField.BIGINT_UNSIGNED
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
    public final MySQLField[] mysqlTypes;

    JavaType(Class<?> type, String defaultMinLength, String defaultMaxLength, MySQLField... mysqlTypes)
    {
        this.type = type;
        if (defaultMaxLength == null) this.defaultMaxLength = null;
        else this.defaultMaxLength = new BigInteger(defaultMaxLength);
        if (defaultMinLength == null) this.defaultMinLength = null;
        else this.defaultMinLength = new BigInteger(defaultMinLength);
        this.mysqlTypes = mysqlTypes;
    }
}
