package net.kigawa.data.database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DatabaseTest extends AbstractDatabaseTest {

    protected Database database;

    @BeforeAll
    public void setUp() {
        database = manager.getDatabase(url, DB_NAME, true);
    }

    @Test
    void update() {
    }

    @Test
    void insert() {
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