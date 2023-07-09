package kr.ac.jbnu.rice.havemeal.model;

public class GlobalStorage {
    private static GlobalStorage globalStorage = null;

    private String userID;

    private GlobalStorage() {
        this.userID = "";
    }

    public static GlobalStorage getInstance() {
        if (globalStorage == null) {
            globalStorage = new GlobalStorage();
        }

        return globalStorage;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
