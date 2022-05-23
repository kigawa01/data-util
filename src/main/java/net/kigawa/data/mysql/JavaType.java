package net.kigawa.data.mysql;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public enum JavaType
{
    STRING(
            String.class,
            "0",
            "255",
            MySQLType.BIT,
            MySQLType.CHAR,
            MySQLType.VARCHAR,
            MySQLType.TINYTEXT,
            MySQLType.TEXT,
            MySQLType.MEDIUMTEXT,
            MySQLType.LONGTEXT
    ),
    BIG_DECIMAL(
            BigDecimal.class,
            null,
            null,
            MySQLType.DECIMAL
    ),
    BOOLEAN(
            Boolean.class,
            "0",
            "1",
            MySQLType.BIT
    ),
    BYTE(
            Byte.class,
            Byte.toString(Byte.MIN_VALUE),
            Byte.toString(Byte.MAX_VALUE),
            MySQLType.BIT,
            MySQLType.TINYINT
    ),
    SHORT(
            Short.class,
            Short.toString(Short.MIN_VALUE),
            Short.toString(Short.MIN_VALUE),
            MySQLType.BIT,
            MySQLType.TINYINT,
            MySQLType.TINYINT_UNSIGNED,
            MySQLType.SMALLINT
    ),
    INTEGER(
            Integer.class,
            Integer.toString(Integer.MIN_VALUE),
            Integer.toString(Integer.MAX_VALUE),
            MySQLType.BIT,
            MySQLType.TINYINT,
            MySQLType.TINYINT_UNSIGNED,
            MySQLType.SMALLINT,
            MySQLType.SMALLINT_UNSIGNED,
            MySQLType.MEDIUMINT,
            MySQLType.MEDIUMINT_UNSIGNED,
            MySQLType.INT
    ),
    LONG(
            Long.class,
            Long.toString(Long.MIN_VALUE),
            Long.toString(Long.MAX_VALUE),
            MySQLType.BIT,
            MySQLType.TINYINT,
            MySQLType.TINYINT_UNSIGNED,
            MySQLType.SMALLINT,
            MySQLType.SMALLINT_UNSIGNED,
            MySQLType.MEDIUMINT,
            MySQLType.MEDIUMINT_UNSIGNED,
            MySQLType.INT,
            MySQLType.INT_UNSIGNED,
            MySQLType.BIGINT
    ),
    BIG_INTEGER(
            BigInteger.class,
            "0",
            "18446744073709551615",
            MySQLType.BIGINT_UNSIGNED
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
    public final MySQLType[] mysqlTypes;

    JavaType(Class<?> type, String defaultMinLength, String defaultMaxLength, MySQLType... mysqlTypes)
    {
        this.type = type;
        if (defaultMaxLength == null) this.defaultMaxLength = null;
        else this.defaultMaxLength = new BigInteger(defaultMaxLength);
        if (defaultMinLength == null) this.defaultMinLength = null;
        else this.defaultMinLength = new BigInteger(defaultMinLength);
        this.mysqlTypes = mysqlTypes;
    }
}
