package net.kigawa.data.mysql;

import java.math.BigInteger;

public enum MysqlType
{
    TINYINT("-128", "127"),

    TINYINT_UNSIGNED("0", "255"),
    SMALLINT("-32768", "32767"),

    SMALLINT_UNSIGNED("0", "65535"),
    MEDIUMINT("-8388608", "8388607"),
    MEDIUMINT_UNSIGNED("0", "16777215"),
    INT("-2147483672", "2147483647"),
    INT_UNSIGNED("0", "4294967295"),

    BIGINT("-9223372036854775808", "9223372036854775807"),

    BIGINT_UNSIGNED("0", "18446744073709551615"),

    FLOAT,
    DOUBLE,

    DECIMAL,
    NUMERIC,

    BIT("1", "64"),

    DATE,
    DATETIME,
    TIMESTAMP,
    TIME,
    YEAR,

    CHAR("0", "255"),
    VARCHAR("0", "65535"),
    TINYTEXT("0", "255"),
    TEXT("0", "65535"),
    MEDIUMTEXT("0", "16777215"),
    LONGTEXT("0", "4294957295"),

    BINARY("0", "255"),
    VARBINARY("0", "65535"),
    TINYBLOB("0", "255"),
    BLOB("0", "65535"),
    MEDIUMBLOB("0", "16777215"),
    LONGBLOB("0", "4294967295"),

    ENUM,

    SET,
    ;

    public final String maxSize;
    public final String minSize;

    <T> MysqlType()
    {
        this(null, null);
    }

    <T> MysqlType(String minSize, String maxSize)
    {
        this.maxSize = maxSize;
        this.minSize = minSize;
    }


    public boolean allowSize(BigInteger size)
    {
        if (maxSize == null || minSize == null || size == null) return true;
        return new BigInteger(minSize).compareTo(size) <= 0 && size.compareTo(new BigInteger(maxSize)) <= 0;
    }
}
