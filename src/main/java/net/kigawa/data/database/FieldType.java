package net.kigawa.data.database;

import java.math.BigInteger;
import java.sql.SQLType;

public interface FieldType
{
    public boolean allowLength(BigInteger size);

    public SQLType getSqlType();
}
