package net.kigawa.data.database;

import net.kigawa.kutil.kutil.KutilFile;
import net.kigawa.kutil.log.log.Formatter;
import net.kigawa.kutil.log.log.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

public class DataBaseManagerTest extends AbstractDatabaseTest {


    @Test
    void getDatabase() throws SQLException {


        connection.prepareStatement("DROP DATABASE IF EXISTS " + DB_NAME).executeUpdate();

        Database database = manager.getDatabase(url, DB_NAME, true);
        Database database1 = manager.getDatabase(url, DB_NAME, true);


        //is there db
        var result = connection.prepareStatement("SHOW DATABASES LIKE '" + DB_NAME + "'").executeQuery();
        assertTrue(result.next());
        assertEquals(DB_NAME, result.getString(1));

        //is same db
        assertNotNull(database);
        assertTrue(database.equalsURL(url));
        assertNotSame(database, database1);
    }

}