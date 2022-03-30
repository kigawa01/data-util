package net.kigawa.data.database;

import net.kigawa.data.javadata.JavaData;
import net.kigawa.kutil.log.log.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Field {
    private final Column column;
    private final Record record;
    private final Logger logger;

    protected Field(Record record, Column column) {
        this.column = column;
        this.record = record;
        logger = record.getLogger();
    }

    public JavaData getData() {
        try {
            createConnection();
            var result = select();
            result.next();
            var data = column.getSqlDataType().getData(column.getName(), result);
            close();
            return data;
        } catch (SQLException e) {
            logger.warning(e);
        }
        return null;
    }

    public <D extends JavaData> D getData(Class<D> dataClass) {
        var data = getData();
        if (dataClass.isInstance(data)) return (D) data;
        return null;
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
