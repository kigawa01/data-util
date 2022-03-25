package net.kigawa.data.database;

import net.kigawa.data.javadata.JavaData;
import net.kigawa.kutil.log.log.Logger;

import java.sql.ResultSet;

public class Field {
    private final Column column;
    private final Record record;
    private final Logger logger;

    protected Field(Record record, Column column) {
        this.column = column;
        this.record = record;
        logger = record.getLogger();
    }

    public void createConnection() {
        record.createConnection();
    }

    public void close() {
        record.close();
    }

    public int update(JavaData data) {
        return record.update(new String[]{column.getName()}, data);
    }

    public ResultSet select() {
        return record.select(new String[]{column.getName()});
    }

    public Column getColumn() {
        return column;
    }
}
