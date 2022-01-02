package net.kigawa.data.cmd;

import net.kigawa.data.sql.AbstractSqlCmd;

import java.util.LinkedList;
import java.util.List;

public class Where<T extends AbstractSqlCmd<T>> {
    private final T cmd;
    private final List<String> cmdStr;

    Where(T cmd, List<String> cmdStr) {
        this.cmd = cmd;
        this.cmdStr = cmdStr;
    }

    public static Where<EmptySqlCmd> getInstance() {
        return new Where<>(new EmptySqlCmd(), new LinkedList<>());
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
