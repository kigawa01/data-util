package net.kigawa.data.database;

import net.kigawa.data.javadata.IntData;
import net.kigawa.data.javadata.StringData;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class TableTest extends AbstractDatabaseTest {

    @Test
    void update() throws SQLException {
        table.createConnection();
        table.update(new String[]{"name"}, "id=0", new StringData("update"));
        table.close();

        var result = connection.prepareStatement("SELECT name FROM " + TABLE_NAME + " WHERE id=0").executeQuery();

        assertTrue(result.next());
        assertEquals("update", result.getString("name"));
    }

    @Test
    void insert() throws SQLException {
        table.createConnection();
        table.insert(new String[]{"id", "name"}, new IntData(1), new StringData("insert"));
        table.close();

        var result = connection.prepareStatement("SELECT name FROM " + TABLE_NAME + " WHERE id=1").executeQuery();

        assertTrue(result.next());
        assertEquals("insert", result.getString("name"));
    }

    @Test
    void select() throws SQLException {
        table.createConnection();
        var result = table.select(new String[]{"name"}, "id=0");

        assertTrue(result.next());
        assertEquals("name", result.getString("name"));

        table.close();
    }

    @Test
    void getRecord() {
    }
}