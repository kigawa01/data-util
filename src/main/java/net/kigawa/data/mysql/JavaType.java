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
            MysqlType.BIT,
            MysqlType.CHAR,
            MysqlType.VARCHAR,
            MysqlType.TINYTEXT,
            MysqlType.TEXT,
            MysqlType.MEDIUMTEXT,
            MysqlType.LONGTEXT
    ),
    BIG_DECIMAL(
            BigDecimal.class,
            null,
            null,
            MysqlType.NUMERIC,
            MysqlType.DECIMAL
    ),
    BOOLEAN(
            Boolean.class,
            "0",
            "1",
            MysqlType.BIT
    ),
    BYTE(
            Byte.class,
            Byte.toString(Byte.MIN_VALUE),
            Byte.toString(Byte.MAX_VALUE),
            MysqlType.BIT,
            MysqlType.TINYINT
    ),
    SHORT(
            Short.class,
            Short.toString(Short.MIN_VALUE),
            Short.toString(Short.MIN_VALUE),
            MysqlType.BIT,
            MysqlType.TINYINT,
            MysqlType.TINYINT_UNSIGNED,
            MysqlType.SMALLINT
    ),
    INTEGER(
            Integer.class,
            Integer.toString(Integer.MIN_VALUE),
            Integer.toString(Integer.MAX_VALUE),
            MysqlType.BIT,
            MysqlType.TINYINT,
            MysqlType.TINYINT_UNSIGNED,
            MysqlType.SMALLINT,
            MysqlType.SMALLINT_UNSIGNED,
            MysqlType.MEDIUMINT,
            MysqlType.MEDIUMINT_UNSIGNED,
            MysqlType.INT
    ),
    LONG(
            Long.class,
            Long.toString(Long.MIN_VALUE),
            Long.toString(Long.MAX_VALUE),
            MysqlType.BIT,
            MysqlType.TINYINT,
            MysqlType.TINYINT_UNSIGNED,
            MysqlType.SMALLINT,
            MysqlType.SMALLINT_UNSIGNED,
            MysqlType.MEDIUMINT,
            MysqlType.MEDIUMINT_UNSIGNED,
            MysqlType.INT,
            MysqlType.INT_UNSIGNED,
            MysqlType.BIGINT
    ),
    BIG_INTEGER(
            BigInteger.class,
            "0",
            "18446744073709551615",
            MysqlType.BIGINT_UNSIGNED
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
    public final MysqlType[] mysqlTypes;

    JavaType(Class<?> type, String defaultMinLength, String defaultMaxLength, MysqlType... mysqlTypes)
    {
        this.type = type;
        if (defaultMaxLength == null) this.defaultMaxLength = null;
        else this.defaultMaxLength = new BigInteger(defaultMaxLength);
        if (defaultMinLength == null) this.defaultMinLength = null;
        else this.defaultMinLength = new BigInteger(defaultMinLength);
        this.mysqlTypes = mysqlTypes;
    }
}
