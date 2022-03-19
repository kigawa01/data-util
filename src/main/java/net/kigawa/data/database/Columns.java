package net.kigawa.data.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Columns {
    private final Map<String, Column> columnMap = new HashMap<>();

    public Columns(Column... columns) {
        for (Column column : columns) {
            columnMap.put(column.getName(), column);
        }
    }

    public boolean contain(Column column) {
        return columnMap.containsValue(column);
    }

    public boolean equalsResultSet(ResultSet resultSet) throws SQLException {
        int size = 0;
        while (resultSet.next()) {
            size++;
            var column = columnMap.get(resultSet.getString("Field"));
            if (column == null) return false;
            if (!column.getDataType().equals(resultSet.getString("Type"))) return false;
            if (!column.canNull() == resultSet.getBoolean("Null")) return false;
            if (!column.getKeyType().equals(resultSet.getString("Key"))) return false;
            if (!column.getDefaultData().equalsData(column.getDataType().getData("Default", resultSet))) return false;
            if (!column.getExtraType().equals(resultSet.getString("Extra"))) return false;
        }
        if (size != columnMap.size()) return false;
        return true;
    }
}
