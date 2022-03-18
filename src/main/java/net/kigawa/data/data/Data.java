package net.kigawa.data.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Data {


    public abstract void addDataToStatement(int index, PreparedStatement statement) throws SQLException;

    public abstract boolean equalsData(Data data);
}
