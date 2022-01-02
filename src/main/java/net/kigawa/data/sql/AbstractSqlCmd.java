package net.kigawa.data.sql;

import net.kigawa.data.cmd.EmptySqlCmd;
import net.kigawa.data.cmd.Where;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractSqlCmd<T> {
    protected final LinkedList<String> cmd = new LinkedList<>();
    protected final List<VarType> varTypes = new ArrayList<>();
    protected final List<Object> objectList = new ArrayList<>();

    protected void addVar(VarType varType, Object var) {
        varTypes.add(varType);
        objectList.add(varType);
    }

    Iterator<VarType> getVarTypes() {
        return varTypes.iterator();
    }

    Iterator<Object> getObjects() {
        return objectList.iterator();
    }

    public String getCmd() {
        final StringBuffer sb = new StringBuffer();

        Iterator<String> iterator = cmd.iterator();
        while (iterator.hasNext()) {
            String cmd = iterator.next();
            sb.append(cmd);
            if (iterator.hasNext()) sb.append(" ");
        }
        return sb.toString();
    }

    public T where(Where<EmptySqlCmd> where) {
        cmd.addAll(where.getCmdStr());
        varTypes.addAll(where.getCmd().varTypes);
        objectList.addAll(where.getCmd().objectList);
        return (T) this;
    }
}
