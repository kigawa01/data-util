package net.kigawa.data.sql;

import java.util.LinkedList;
import java.util.List;

import static net.kigawa.data.sql.Sql.*;

public class Where<T extends AbstractSqlCmd<T>> {
    private final T cmd;
    private final List<String> cmdStr;

    Where(T cmd, List<String> cmdStr) {
        this.cmd = cmd;
        this.cmdStr = cmdStr;
        cmdStr.add(WHERE);
    }

    public static Where<EmptySqlCmd> getInstance() {
        return new Where<>(new EmptySqlCmd(), new LinkedList<>());
    }

    public Where<T> add(String column, VarType varType, Object o) {
        cmdStr.add(column);
        cmdStr.add("= ?");

        cmd.addVar(varType, o);
        return this;
    }

    public Where<T> and() {
        cmdStr.add(AND);
        return this;
    }

    public Where<T> or() {
        cmdStr.add(OR);
        return this;
    }

    public T create() {

        return cmd;
    }

    public T getCmd() {
        return cmd;
    }

    public List<String> getCmdStr() {
        return cmdStr;
    }
}
