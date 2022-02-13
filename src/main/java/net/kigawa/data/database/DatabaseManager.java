package net.kigawa.data.database;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager  {
    protected List<Database> databaseList = new ArrayList<>();

    public Database getDataBase(String url) {
        for (Database dataBase : databaseList) {
            if (dataBase.equalsURL(url)) return dataBase;
        }
        var database = new Database(url);
        databaseList.add(database);
        return database;
    }
}
