package net.kigawa.data.database;

import net.kigawa.data.data.JavaData;
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

    public int update(String[] columns, JavaData... data) {
        return record.update(columns, data);
    }

    public int insert(String[] columns, JavaData... data) {
        return record.insert(columns, data);
    }

    public ResultSet select(String[] columns, JavaData... data) {
        return record.select(columns, data);
    }


    public Column getColumn() {
        return column;
    }
}
