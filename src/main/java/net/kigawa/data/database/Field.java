package net.kigawa.data.database;

import net.kigawa.kutil.log.log.Logger;

public class Field {
    private final Column column;
    private final Record record;
    private final Logger logger;

    protected Field(Record record, Column column) {
        this.column = column;
        this.record = record;
        logger = record.getLogger();
    }

    public Column getColumn() {
        return column;
    }
}
