package net.kigawa.data.sql;

import net.kigawa.data.function.ThrowTriConsumer;

import java.sql.PreparedStatement;

public enum VarType {
    STRING((p, i, o) -> p.setString(i, (String) o)),
    ;
    private final ThrowTriConsumer<PreparedStatement, Integer, Object> setStatement;

    VarType(ThrowTriConsumer<PreparedStatement, Integer, Object> setStatement) {
        this.setStatement = setStatement;
    }

    void setStatement(PreparedStatement statement,int index,Object o) throws Exception {
        setStatement.accept(statement,index,o);
    }
}
