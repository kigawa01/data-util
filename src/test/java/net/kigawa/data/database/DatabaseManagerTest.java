package net.kigawa.data.database;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class DatabaseManagerTest extends AbstractDatabaseTest {


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