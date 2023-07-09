package kr.ac.jbnu.rice.havemeal.model;

public class GlobalStorage {
    private static GlobalStorage globalStorage = null;

    private GlobalStorage() {

    }

    public static GlobalStorage getInstance() {
        if (globalStorage == null) {
            globalStorage = new GlobalStorage();
        }

        return globalStorage;
    }

}
