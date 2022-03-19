package net.kigawa.data.data;

import java.sql.ResultSet;

public abstract class DataType<D extends Data> {

    public abstract D getData(String key, ResultSet resultSet);

    public abstract boolean equals(String name);
}
