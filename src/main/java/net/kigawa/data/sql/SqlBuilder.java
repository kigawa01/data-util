package net.kigawa.data.sql;

import net.kigawa.data.database.DatabaseField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

public class SqlBuilder
{
    private final StringBuffer stringBuffer = new StringBuffer();
    private final LinkedList<DatabaseField> databaseFields = new LinkedList<>();

    public SqlBuilder append(String str)
    {
        stringBuffer.append(str);
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
        stringBuffer.append(str).append(" ");
        return this;
    }

    public SqlBuilder add(DatabaseField databaseField)
    {
        add("?");
        databaseFields.add(databaseField);
        return this;
    }

    public PreparedStatement getStatement(Connection connection) throws SQLException
    {
        var statement = connection.prepareStatement(stringBuffer.toString());
        for (int i = 0; i < databaseFields.size(); ) {
            var databaseField = databaseFields.get(i);
            databaseField.setValueToStatement(statement, ++i);
        }
        return statement;
    }
}
