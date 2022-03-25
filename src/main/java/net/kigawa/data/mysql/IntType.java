package net.kigawa.data.mysql;

import net.kigawa.data.data.SqlDataType;
import net.kigawa.data.data.IntData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntType extends SqlDataType<IntData> {

    @Override
    public IntData getData(String key, ResultSet resultSet) throws SQLException {
        return new IntData(resultSet.getInt(key));
    }

    @Override
    public String getSql() {
        return "int";
    }

    @Override
    public boolean equals(String name) {
        return "int".equals(name);
    }
}
