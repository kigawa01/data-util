package net.kigawa.data.database;

import net.kigawa.data.data.Data;
import net.kigawa.kutil.kutil.KutilString;
import net.kigawa.kutil.log.log.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Database {
    private final String url;
    private final String name;
    private final Set<Table> tableSet = new HashSet<>();
    private final Logger logger;
    private final boolean migrate;
    private Connection connection;
    private int session;

    protected Database(Logger logger, String url, String name, boolean migrate) {
        this.url = url;
        this.name = name;
        this.logger = logger;
        this.migrate = migrate;

        createConnection();
        if (migrate) migrate();
        executeUpdate("USE " + name);
        close();
    }

    public int delete(String table, String where, Data... data) {
        var sb = new StringBuffer("DELETE FROM ").append(table);
        if (where != null) {
            sb.append(" WHERE ").append(where);
        }
        return executeUpdate(sb.toString(), data);
    }

    public int update(String table, String[] columns, String where, Data... data) {
        var sb = new StringBuffer("UPDATE ").append(table).append(" SET ");
        KutilString.insertSymbol(sb, ",", columns, column -> column + "=?");
        if (where != null) {
            sb.append(" WHERE ").append(where);
        }
        return executeUpdate(sb.toString(), data);
    }

    public int insert(String table, String[] columns, Data... data) {
        var sb = new StringBuffer("INSERT INTO ").append(table).append("(");
        KutilString.insertSymbol(sb, ",", columns);
        sb.append(") VALUES(");
        KutilString.insertSymbol(sb, ",", columns, column -> "?");
        sb.append(")");
        return executeUpdate(sb.toString(), data);
    }

    public ResultSet select(String table, String[] columns, String where, Data... data) {
        var sb = new StringBuffer("SELECT ");
        KutilString.insertSymbol(sb, ",", columns);
        sb.append(" FROM ").append(table);
        if (where != null) {
            sb.append(" WHERE ").append(where);
        }
        return executeQuery(sb.toString(), data);
    }

    public void remove(Table table) {
        tableSet.remove(table);
    }

    private void migrate() {
        logger.fine("migrate DB: " + name);
        if (canUse()) return;
        createDB();
    }

    private void createDB() {
        logger.info("create DB: " + name);
        executeUpdate("CREATE DATABASE IF NOT EXISTS " + name);
        executeUpdate("USE " + name);
    }

    public int executeUpdate(String sql, Data... data) {
        try {
            var st = getPreparedStatement(sql);
            if (st == null) return -1;
            for (int i = 0; i < data.length; i++) {
                data[i].addDataToStatement(i + 1, st);
            }
            var result = st.executeUpdate();
            st.close();
            return result;
        } catch (SQLException e) {
            logger.warning(e);
            return -1;
        }
    }

    public ResultSet executeQuery(String sql, Data... data) {
        try {
            var st = getPreparedStatement(sql);
            if (st == null) {
                logger.warning("can not execute");
                return null;
            }
            for (int i = 0; i < data.length; i++) {
                data[i].addDataToStatement(i + 1, st);
            }
            return st.executeQuery();
        } catch (SQLException e) {
            logger.warning(e);
            return null;
        }
    }

    public synchronized void close() {
        try {
            session--;
            if (session > 0) return;
            if (connection == null) return;
            if (!connection.isClosed()) connection.close();
            connection = null;
        } catch (SQLException e) {
            logger.warning(e);
        }
    }

    public PreparedStatement getPreparedStatement(String sql) {
        try {
            var connection = getConnection();
            if (connection == null) {
                logger.warning("can not create statement");
                return null;
            }
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            logger.warning(e);
            return null;
        }
    }

    public synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                logger.warning("connection is closed!");
                return null;
            }
            return connection;
        } catch (Exception e) {
            logger.warning(e);
            return null;
        }
    }

    public void createConnection() {
        session = 0;
        try {
            session++;
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url);
                executeUpdate("USE " + name);
            }
        } catch (Exception e) {
            logger.warning(e);
            connection = null;
        }
    }

    public boolean canUse() {
        if (!isExist()) return false;
        return true;
    }

    public boolean isExist() {
        try {
            ResultSet resultSet = executeQuery("SELECT database()");
            if (resultSet == null || !resultSet.next()) return false;
            return name.equalsIgnoreCase(resultSet.getString("database()"));
        } catch (SQLException e) {
            logger.warning(e);
            return false;
        }
    }

    public Table getTable(String name, Columns columns, boolean migrate) {
        for (Table table : tableSet) {
            if (table.equalsName(name) && table.equalsColumn(columns)) return table;
            if (table.equalsName(name)) return null;
        }
        var table = new Table(logger, this, name, columns, migrate);
        tableSet.add(table);
        return table;
    }

    public void deleteDB() {
        logger.info("delete DB \"" + name + "\"");
        try {
            getPreparedStatement("DROP DATABASE IF EXIST " + name).executeUpdate();
            close();
        } catch (SQLException e) {
            logger.warning(e);
        }
    }

    public boolean equalsURL(String url) {
        return this.url.equals(url);
    }
}
