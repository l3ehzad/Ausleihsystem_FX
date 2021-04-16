package application;

public class AlleArtikelIntermediate {
    int devID, available, invID;
    String devName, lableName;

    public AlleArtikelIntermediate(int devID, String devName, String lableName, int available, int invID) {
        this.devID = devID;
        this.available = available;
        this.invID = invID;
        this.devName = devName;
        this.lableName = lableName;
    }

    public int getDevID() {
        return devID;
    }

    public int getAvailable() {
        return available;
    }

    public int getInvID() {
        return invID;
    }

    public String getDevName() {
        return devName;
    }

    public String getLableName() {
        return lableName;
    }

    public void setDevID(int devID) {
        this.devID = devID;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setInvID(int invID) {
        this.invID = invID;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public void setLableName(String lableName) {
        this.lableName = lableName;
    }
}
