package net.kigawa.data.database;

import net.kigawa.data.sql.ColumnList;
import net.kigawa.util.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataBase implements Iterable<Table> {
    private final String name;
    private final List<Table> tableList = new ArrayList<>();

    protected DataBase(String name) {
        this.name = name;
    }

    public Table createTable(String name, ColumnList labels, Recorde... columns) {
        Table table = getTable(name);
        if (table != null) {
            Logger.getInstance().info(name + " is exist");
            return table;
        }

        table = new Table(name, this, labels, columns);
        tableList.add(table);
        return table;
    }

    public Table getTable(String name) {
        for (Table table : tableList) {
            if (table.equals(name)) return table;
        }
        return null;
    }

    @Override
    public Iterator<Table> iterator() {
        return tableList.listIterator();
    }

}
