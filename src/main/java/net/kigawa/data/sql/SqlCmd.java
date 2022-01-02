package net.kigawa.data.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class SqlCmd {
    List<Class> classList = new ArrayList<>();
    List<Object> objectList = new ArrayList<>();

    Iterator<Class> getClasses() {
        return classList.iterator();
    }

    Iterator<Object> getObjects() {
        return objectList.iterator();
    }

    public abstract String getCmd();
}
