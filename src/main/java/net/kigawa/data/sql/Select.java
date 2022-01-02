package net.kigawa.data.sql;

import net.kigawa.StringUtil;

import java.util.Collections;
import java.util.LinkedList;

import static net.kigawa.data.sql.Sql.*;

public class Select extends AbstractSqlCmd<Select> {
    private final String table;
    private LinkedList<String> columnList;
    private boolean distinct = false;

    public Select(String table) {
        this.table = table;
    }

    public Select distinct() {
        distinct = true;
        return this;
    }

    public Select columns(String... columns) {
        if (columnList == null) columnList = new LinkedList<>();
        Collections.addAll(columnList, columns);
        return this;
    }

    public Select columnsAs(String column, String as) {
        if (columnList == null) columnList = new LinkedList<>();
        columnList.add(column);
        columnList.add(as);
        return this;
    }

    public Select create() {
        cmd.clear();
        cmd.add(SELECT);

        if (distinct) cmd.add(DISTINCT);

        if (columnList == null) cmd.add("*");
        else {
            cmd.add(StringUtil.insertSymbol(",", columnList));
        }

        cmd.add(FROM);
        cmd.add(table);

        return this;
    }

    @Override
    protected void addVar(VarType varType, Object o) {
        super.addVar(varType, o);
    }
}
