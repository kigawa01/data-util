package net.kigawa.data.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IntData extends Data {
    private final int i;

    public IntData(int i) {
        this.i = i;
    }

    @Override
    public void addDataToStatement(int index, PreparedStatement statement) throws SQLException {
        statement.setInt(index, i);
    }

    @Override
    public boolean equalsData(Data data) {
        if (data instanceof IntData) return equals((IntData) data);
        return false;
    }

    public boolean equals(IntData data) {
        if (data == null) return false;
        return data.i == i;
    }
}
