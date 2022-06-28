package net.kigawa.data.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

public class SqlBuilder
{
    private final StringBuffer stringBuffer = new StringBuffer();
    private final LinkedList<DatabaseTypeField> databaseTypeFields = new LinkedList<>();

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

    public SqlBuilder add(DatabaseTypeField databaseField)
    {
        add("?");
        databaseTypeFields.add(databaseField);
        return this;
    }

    public PreparedStatement getStatement(Connection connection) throws SQLException
    {
        var statement = connection.prepareStatement(stringBuffer.toString());
        for (int i = 0; i < databaseTypeFields.size(); ) {
            var databaseField = databaseTypeFields.get(i);
            databaseField.setValue(statement, ++i);
        }
        return statement;
    }
}
