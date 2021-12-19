package net.kigawa.data;

import net.kigawa.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager dataManager;
    private List<DataBase> dataBaseList = new ArrayList<>();

    private DataManager() {

    }

    public static DataManager getInstance() {
        if (dataManager == null) new DataManager();
        return dataManager;
    }

    public DataBase createDataBase(String name) {
        DataBase dataBase = getDataBase(name);
        if (dataBase != null) {
            Logger.getInstance().info(name + " is exist");
            return dataBase;
        }

        dataBase = new DataBase(name);
        dataBaseList.add(dataBase);
        return dataBase;
    }

    public DataBase getDataBase(String name) {
        for (DataBase dataBase : dataBaseList) {
            if (dataBase.equals(name)) return dataBase;
        }
        return null;
    }
}
