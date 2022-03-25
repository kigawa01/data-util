package net.kigawa.data.javadata;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IntData extends JavaData {
    private final int i;

    public IntData(int i) {
        this.i = i;
    }

    @Override
    public void addDataToStatement(int index, PreparedStatement statement) throws SQLException {
        statement.setInt(index, i);
    }

    public int get() {
        return i;
    }

    @Override
    public boolean equalsData(JavaData javaData) {
        if (javaData instanceof IntData) return equals((IntData) javaData);
        return false;
    }

    public boolean equals(IntData data) {
        if (data == null) return false;
        return data.i == i;
    }
}
