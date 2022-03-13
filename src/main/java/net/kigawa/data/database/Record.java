package net.kigawa.data.database;

import net.kigawa.data.data.Data;

import java.util.logging.Logger;

public class Record {
    private final Columns columns;
    private final Data key;
    private final Logger logger;
    private final Table table;

    protected Record(Logger logger, Table table, Columns columns, Data key) {
        this.columns = columns;
        this.key = key;
        this.logger = logger;
        this.table = table;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Record) {
            return equalsRecord((Record) o);
        }
        return false;
    }

    public boolean equalsRecord(Record record) {
        return equalsTable(record.table) && equalsKey(record.key);
    }

    public boolean equalsTable(Table table) {
        return this.table.equalsTable(table);
    }

    public boolean equalsKey(Data key) {

    }
}
