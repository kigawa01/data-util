package net.kigawa.data.mysql;

import java.math.BigInteger;

public enum MysqlType
{
    TINYINT(Byte.class, "-128", "127"),

    TINYINT_UNSIGNED(Short.class, "0", "255"),
    SMALLINT(Short.class, "-32768", "32767"),

    SMALLINT_UNSIGNED(Integer.class, "0", "65535"),
    MEDIUMINT(Integer.class, "-8388608", "8388607"),
    MEDIUMINT_UNSIGNED(Integer.class, "0", "16777215"),
    INT(Integer.class, "-2147483672", "2147483647"),
    INT_UNSIGNED(Integer.class, "0", "4294967295"),

    BIGINT(Long.class, "-9223372036854775808", "9223372036854775807"),

    BIGINT_UNSIGNED(BigInteger.class, "0", "18446744073709551615"),

    CHAR(String.class, "0", "255"),
    VARCHAR(String.class, "0", "65535"),
    TINYTEXT(String.class, "0", "255"),
    TEXT(String.class, "0", "65535"),
    MEDIUMTEXT(String.class, "0", "16777215"),
    LONGTEXT(String.class, "0", "4294957295"),

    BINARY(Byte[].class, "0", "255"),
    VARBINARY(Byte[].class, "0", "65535"),

    FLOAT(Float.class),

    DOUBLE(Double.class);

    public final Class<?> cla;
    public final String maxSize;
    public final String minSize;

    <T> MysqlType(Class<T> cla)
    {
        this(cla, null, null);
    }

    <T> MysqlType(Class<T> cla, String minSize, String maxSize)
    {
        this.cla = cla;
        this.maxSize = maxSize;
        this.minSize = minSize;
    }


    public boolean allowSize(BigInteger size)
    {
        if (maxSize == null || minSize == null || size == null) return true;
        return new BigInteger(minSize).compareTo(size) <= 0 && size.compareTo(new BigInteger(maxSize)) <= 0;
    }

    public MysqlType getDefault(Class<?> cla, BigInteger size)
    {
        for (var type : values()) {
            if (type.cla.equals(cla) && allowSize(size)) return type;
        }
        return null;
    }
}
