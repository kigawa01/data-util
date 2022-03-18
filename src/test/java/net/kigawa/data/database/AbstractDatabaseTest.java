package net.kigawa.data.database;

import net.kigawa.kutil.kutil.KutilFile;
import net.kigawa.kutil.log.log.Formatter;
import net.kigawa.kutil.log.log.Logger;
import org.junit.jupiter.api.Assertions;

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
    public static String DB_NAME = "test";
    public static String PORT = "3306";
    public static String password;
    public static String url;
    protected final Logger logger = new Logger("test", null, Level.INFO, null);
    protected Connection connection;

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
        url = "jdbc:" + DB_TYPE + "://" + USER + ":" + password + "@" + HOST + ":" + PORT;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.warning(e);
        }
    }
}
