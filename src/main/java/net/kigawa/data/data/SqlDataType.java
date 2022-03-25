package net.kigawa.data.data;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SqlDataType<D extends JavaData> {

    public abstract D getData(String key, ResultSet resultSet) throws SQLException;

    public abstract String getSql();

    public abstract boolean isAllow(JavaData javaData);

    public abstract boolean equals(String name);
}