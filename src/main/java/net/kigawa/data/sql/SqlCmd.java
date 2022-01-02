package net.kigawa.data.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class SqlCmd {
    protected List<VarType> classList = new ArrayList<>();
    protected List<Object> objectList = new ArrayList<>();

    Iterator<VarType> getVarTypes() {
        return classList.iterator();
    }

    Iterator<Object> getObjects() {
        return objectList.iterator();
    }

    public abstract String getCmd();
}
