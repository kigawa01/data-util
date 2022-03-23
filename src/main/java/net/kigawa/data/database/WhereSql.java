package net.kigawa.data.database;

public class WhereSql {
    private String sql;

    public WhereSql(String sql) {
        this.sql = sql;
    }

    public String toSql() {
        return sql;
    }

    public boolean equalsWhereSql(WhereSql whereSql) {
        if (whereSql == null) return false;
        return sql.equals(whereSql.sql);
    }
}
