package net.kigawa.data.javadata;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JavaData {


    public abstract void addDataToStatement(int index, PreparedStatement statement) throws SQLException;

    public abstract boolean equalsData(JavaData javaData);
}
