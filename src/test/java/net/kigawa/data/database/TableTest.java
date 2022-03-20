package net.kigawa.data.database;

import net.kigawa.data.data.IntData;
import net.kigawa.data.data.StringData;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class TableTest extends AbstractDatabaseTest {

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
    void insert() {
        table.createTable();
        table.insert(new String[]{"id","name"},new IntData(1),new StringData("insert"));
        table.close();

        var result=connection.prepareStatement("SELECT name FROM "+TABLE_NAME+" WHERE id=1").executeQuery();

        assertTrue(result.next());
    }

    @Test
    void select() {
    }

    @Test
    void canUse() {
    }

    @Test
    void isExist() {
    }

    @Test
    void isSyncColumn() {
    }

    @Test
    void migrate() {
    }

    @Test
    void createTable() {
    }

    @Test
    void deleteTable() {
    }

    @Test
    void getRecord() {
    }
}