package net.kigawa.data.mysql;

import com.mysql.cj.MysqlType;

import java.math.BigInteger;

public enum MySQLType
{
    BIT(MysqlType.BIT, "0", "64"),

    TINYINT(MysqlType.TINYINT, "-128", "127"),

    TINYINT_UNSIGNED(MysqlType.TINYINT_UNSIGNED, "0", "255"),
    SMALLINT(MysqlType.SMALLINT, "-32768", "32767"),

    SMALLINT_UNSIGNED(MysqlType.SMALLINT_UNSIGNED, "0", "65535"),
    MEDIUMINT(MysqlType.MEDIUMINT, "-8388608", "8388607"),
    MEDIUMINT_UNSIGNED(MysqlType.MEDIUMINT_UNSIGNED, "0", "16777215"),
    INT(MysqlType.INT, "-2147483672", "2147483647"),
    INT_UNSIGNED(MysqlType.INT_UNSIGNED, "0", "4294967295"),

    BIGINT(MysqlType.BIGINT, "-9223372036854775808", "9223372036854775807"),

    BIGINT_UNSIGNED(MysqlType.BIGINT_UNSIGNED, "0", "18446744073709551615"),

    FLOAT(MysqlType.FLOAT),
    DOUBLE(MysqlType.DOUBLE),

    DECIMAL(MysqlType.DECIMAL),

    DATE(MysqlType.DATE),
    DATETIME(MysqlType.DATETIME),
    TIMESTAMP(MysqlType.TIMESTAMP),
    TIME(MysqlType.TIME),
    YEAR(MysqlType.YEAR),

    CHAR(MysqlType.CHAR, "0", "255"),
    VARCHAR(MysqlType.VARCHAR, "0", "65535"),
    TINYTEXT(MysqlType.TINYTEXT, "0", "255"),
    TEXT(MysqlType.TEXT, "0", "65535"),
    MEDIUMTEXT(MysqlType.MEDIUMTEXT, "0", "16777215"),
    LONGTEXT(MysqlType.LONGTEXT, "0", "4294957295"),

    BINARY(MysqlType.BINARY, "0", "255"),
    VARBINARY(MysqlType.VARBINARY, "0", "65535"),
    TINYBLOB(MysqlType.TINYBLOB, "0", "255"),
    BLOB(MysqlType.BLOB, "0", "65535"),
    MEDIUMBLOB(MysqlType.MEDIUMBLOB, "0", "16777215"),
    LONGBLOB(MysqlType.LONGBLOB, "0", "4294967295"),

    ENUM(MysqlType.ENUM),

    SET(MysqlType.SET),
    ;

    public final BigInteger maxLength;
    public final BigInteger minLength;
    public final MysqlType sqlType;

    MySQLType(MysqlType sqlType)
    {
        this(sqlType, null, null);
    }

    MySQLType(MysqlType sqlType, String minLength, String maxLength)
    {
        this.sqlType = sqlType;

        if (maxLength == null) this.maxLength = null;
        else this.maxLength = new BigInteger(maxLength);

        if (minLength == null) this.minLength = null;
        else this.minLength = new BigInteger(minLength);
    }


    public boolean allowLength(BigInteger size)
    {
        if (maxLength == null || minLength == null || size == null) return true;
        return minLength.compareTo(size) <= 0 && size.compareTo(maxLength) <= 0;
    }
}
