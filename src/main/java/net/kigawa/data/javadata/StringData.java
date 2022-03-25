package net.kigawa.data.javadata;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StringData extends JavaData {
    private final String str;

    public StringData(String str) {
        this.str = str;
    }

    @Override
    public void addDataToStatement(int index, PreparedStatement statement) throws SQLException {
        statement.setString(index, str);
    }

    public String get() {
        return str;
    }

    @Override
    public boolean equalsData(JavaData javaData) {
        if (javaData instanceof StringData) return equals((StringData) javaData);
        return false;
    }

    public boolean equals(StringData data) {
        if (data == null) return false;
        return str.equals(data.str);
    }
}
