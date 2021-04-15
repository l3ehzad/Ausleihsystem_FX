package application;

public class AvailableItemsIntermediate {
    private int devID;
    private String devName;
    private String devLable;

    public AvailableItemsIntermediate(int devID, String devName, String devLable) {
        this.devID = devID;
        this.devName = devName;
        this.devLable = devLable;
    }

    public void setDevID(int devID) {
        this.devID = devID;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public void setDevLable(String devLable) {
        this.devLable = devLable;
    }

    public int getDevID() {
        return devID;
    }

    public String getDevName() {
        return devName;
    }

    public String getDevLable() {
        return devLable;
    }
}
