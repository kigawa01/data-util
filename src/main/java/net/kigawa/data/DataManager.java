package net.kigawa.data;

public class DataManager {
    private static DataManager dataManager;

    private DataManager() {

    }

    public static DataManager getInstance() {
        if (dataManager == null) new DataManager();
        return dataManager;
    }
}
