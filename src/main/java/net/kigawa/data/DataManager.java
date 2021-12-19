package net.kigawa.data;

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
}
