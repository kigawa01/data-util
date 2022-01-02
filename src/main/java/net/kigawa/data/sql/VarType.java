package net.kigawa.data.sql;

import net.kigawa.data.function.TriConsumer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public enum VarType {
    STRING(new TriConsumer<PreparedStatement, Integer, Object>() {
        @Override
        public void accept(PreparedStatement statement, Integer integer, Object o) {
            try {
                if (o instanceof String) {
                    statement.setString(integer, (String) o);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }),
    ;
    private final TriConsumer<PreparedStatement, Integer, Object> setStatement;

    VarType(TriConsumer<PreparedStatement, Integer, Object> setStatement) {
        this.setStatement = setStatement;
    }
}
