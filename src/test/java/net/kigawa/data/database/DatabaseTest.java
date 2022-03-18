package net.kigawa.data.database;

import net.kigawa.data.data.Data;
import net.kigawa.data.data.StringData;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

class DatabaseTest extends AbstractDatabaseTest {

    protected Database database;


    public DatabaseTest() {
        database = manager.getDatabase(url, DB_NAME, true);
    }

    @Test
    void update() throws SQLException {
        database.createConnection();
        database.update(TABLE_NAME, new String[]{"name"}, "id=0", new StringData("update"));
        database.close();

        var result = connection.prepareStatement("SELECT name FROM " + TABLE_NAME + " WHERE id=0").executeQuery();

        assertTrue(result.next());
        assertEquals(result.getString("name"), "update");
    }

    @Test
    void insert() {
        database.createConnection();
        database.insert(TABLE_NAME, new String[]{"id","name"},);
        database.close();

        var result = connection.prepareStatement("SELECT name FROM " + TABLE_NAME + " WHERE id=0").executeQuery();

        assertTrue(result.next());
        assertEquals(result.getString("name"), "update");
    }

    @Test
    void select() {
    }

    @Test
    void delete() {
    }

    @Test
    void migrate() {
    }

    @Test
    void createDB() {
    }

    @Test
    void executeUpdate() {
    }

    @Test
    void executeQuery() {
    }

    @Test
    void close() {
    }

    @Test
    void getPreparedStatement() {
    }

    @Test
    void getConnection() {
    }

    @Test
    void createConnection() {
    }

    @Test
    void canUse() {
    }

    @Test
    void isExist() {
    }

    @Test
    void getTable() {
    }

    @Test
    void deleteDB() {
    }

    @Test
    void equalsURL() {
    }

}