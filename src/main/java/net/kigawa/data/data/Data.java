package net.kigawa.data.data;

import java.sql.PreparedStatement;

public abstract class Data {


    public abstract void addDataToStatement(PreparedStatement statement);

    public abstract boolean equalsData(Data data);
}
