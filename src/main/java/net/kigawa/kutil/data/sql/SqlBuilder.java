package net.kigawa.kutil.data.sql;

import net.kigawa.kutil.data.database.DatabaseField;
import net.kigawa.kutil.kutil.KutilString;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

public class SqlBuilder
{
    private final LinkedList<String> sql = new LinkedList<>();
    private final LinkedList<SqlValue> databaseFields = new LinkedList<>();

    public SqlBuilder add(SqlBuilder sqlBuilder)
    {
        sql.addAll(sqlBuilder.sql);
        databaseFields.addAll(sqlBuilder.databaseFields);
        return this;
    }

    public SqlBuilder add(String... strings)
    {
        for (String str : strings) {
            add(str);
        }
        return this;
    }

    public SqlBuilder add(String str)
    {
        sql.add(str);
        return this;
    }

    public SqlBuilder addField(SqlValue sqlValue)
    {
        add("?");
        databaseFields.add(sqlValue);
        return this;
    }

    public void removeLatestSql()
    {
        sql.removeLast();
    }

    public PreparedStatement getStatement(Connection connection) throws SQLException
    {
        var statement = connection.prepareStatement(
                KutilString.insertSymbol(new StringBuffer(), " ", sql).toString()
        );
        for (int i = 0; i < databaseFields.size(); ) {
            var sqlValue = databaseFields.get(i);
            sqlValue.writeStatement(statement, ++i);
        }
        return statement;
    }
}
