package net.kigawa.data.database;

import net.kigawa.data.data.IntData;
import net.kigawa.data.mysql.IntType;
import net.kigawa.kutil.kutil.KutilFile;
import net.kigawa.kutil.log.log.Formatter;
import net.kigawa.kutil.log.log.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;

public abstract class AbstractDatabaseTest extends Assertions {
    public static String DB_TYPE = "mysql";
    public static String USER = "kigawa";
    public static String HOST = "192.168.0.18";
    public static String DB_NAME = "test_db";
    public static String TABLE_NAME = "test_table";
    public static String PORT = "3306";
    public static String password;
    public static String url;
    protected final Logger logger = new Logger("test", null, Level.INFO, null);
    protected Columns columns;
    protected Connection connection;
    protected DataBaseManager manager;
    protected Database database;
    protected Table table;
    protected Record record;
    protected Field field;

    public AbstractDatabaseTest() {
        logger.enable();

        for (Handler handler : logger.getParent().getHandlers()) {
            if (handler instanceof ConsoleHandler) handler.setFormatter(new Formatter());
        }
        File config = KutilFile.getRelativeFile("test.pass");

        try {
            if (!config.exists()) config.createNewFile();
            password = new BufferedReader(new FileReader(config)).readLine();
        } catch (IOException e) {
            logger.warning(e);
        }
        url = "jdbc:" + DB_TYPE + "://" + USER + ":" + password + "@" + HOST + ":" + PORT + "/";
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.warning(e);
        }

        manager = new DataBaseManager(logger);
        manager.enable();

        columns = new Columns(logger, new Column("id", new IntType(), true, null, null, null));
        database = manager.getDatabase(url, DB_NAME, false);
        table = database.getTable(TABLE_NAME, columns, false);
        record = table.getRecord(new IntData(0), false);
        field = record.getField("name");
    }

    @BeforeEach
    public void setUpDatabase() {
        try {
            connection.prepareStatement("DROP DATABASE IF EXISTS " + DB_NAME).executeUpdate();
            connection.prepareStatement("CREATE DATABASE IF NOT EXISTS " + DB_NAME).executeUpdate();
            connection.prepareStatement("USE " + DB_NAME).executeUpdate();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(id int, name varchar(10))").executeUpdate();
            connection.prepareStatement("INSERT INTO " + TABLE_NAME + "(id,name) VALUES(0,'name')").executeUpdate();
        } catch (SQLException e) {
            logger.warning(e);
        }
    }
}
