package net.kigawa.data.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StringData extends Data {
    private final String str;

    public StringData(String str) {
        this.str = str;
    }

    @Override
    public void addDataToStatement(int index, PreparedStatement statement) throws SQLException {
        statement.setString(index, str);
    }

    @Override
    public boolean equalsData(Data data) {
        if (data instanceof StringData) return equals((StringData) data);
        return false;
    }

    public boolean equals(StringData data) {
        if (data == null) return false;
        return str.equals(data.str);
    }
}
