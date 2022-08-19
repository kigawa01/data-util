package net.kigawa.kutil.data.sql;

import java.sql.PreparedStatement;

public interface SqlValue
{
    void writeStatement(PreparedStatement statement, int index);
}
