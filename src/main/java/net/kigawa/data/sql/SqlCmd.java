package net.kigawa.data.sql;

import java.util.*;

public abstract class SqlCmd {
    protected final LinkedList<String> cmd = new LinkedList<>();
    private final List<VarType> varTypes = new ArrayList<>();
    private final List<Object> objectList = new ArrayList<>();

    public SqlCmd(String... command) {
        cmd.addAll(Arrays.asList(command));
    }

    protected SqlCmd(){}

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
}
