package net.kigawa.data.sql;

import net.kigawa.data.DataLogger;
import net.kigawa.util.Logger;

import java.sql.*;
import java.util.Iterator;

public abstract class Sql {
    public static final String SELECT = "SELECT";
    public static final String FROM = "FROM";
    public static final String DISTINCT="DISTINCT";
    public static final String WHERE="WHERE";
    private final String url;

    public Sql(SqlType sqlType, String url) {
        try {
            Class.forName(sqlType.getClassName());
        } catch (ClassNotFoundException e) {
            Logger.getInstance().warning(e);
        }
        this.url = url;
    }

    public abstract boolean use(String name);

    public int update(Connection connection, AbstractSqlCmd sqlCmd) {
        try {
            PreparedStatement statement = setCmd(connection, sqlCmd);
            if (statement == null) return -1;
            return statement.executeUpdate();
        } catch (SQLException e) {
            DataLogger.getInstance().warning(e);
        }
        return -1;
    }

    public ResultSet query(Connection connection, AbstractSqlCmd sqlCmd) {
        try {
            PreparedStatement statement = setCmd(connection, sqlCmd);
            if (statement == null) return null;
            return statement.executeQuery();
        } catch (SQLException e) {
            DataLogger.getInstance().warning(e);
        }
        return null;
    }

    private PreparedStatement setCmd(Connection connection, AbstractSqlCmd sqlCmd) {
        try {
            PreparedStatement statement = connection.prepareStatement(sqlCmd.getCmd());
            Iterator<VarType> classIterator = sqlCmd.getVarTypes();
            Iterator<Object> varIterator = sqlCmd.getObjects();
            int index = 0;
            while (classIterator.hasNext() && varIterator.hasNext()) {
                VarType varType = classIterator.next();
                Object o = varIterator.next();
                varType.setStatement(statement, index, o);
                index++;
            }
            return statement;

        } catch (Exception e) {
            DataLogger.getInstance().warning(e);
        }
        return null;
    }

    public void close(Connection connection) {
        Logger.getInstance().info("closing...");
        try {
            if (connection == null) return;
            if (connection.isClosed()) return;
            connection.close();
        } catch (SQLException e) {
            Logger.getInstance().warning(e);
        }
    }

    public Connection connect() {
        Logger.getInstance().info("connecting...");
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            Logger.getInstance().warning(e);
        }
        return null;
    }
}
