package application;

public class SearchIdIntermediate {
    String dshsID;
    String firstName;
    String lastName;
    int deviceId;
    String borrowedDate;
    String deviceName;
    String deviceLable;
    String InventType;

    public SearchIdIntermediate(String dshsID, String firstName, String lastName, int deviceId, String borrowedDate, String deviceName, String deviceLable, String inventType) {
        this.dshsID = dshsID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deviceId = deviceId;
        this.borrowedDate = borrowedDate;
        this.deviceName = deviceName;
        this.deviceLable = deviceLable;
        InventType = inventType;
    }

    public void setDshsID(String dshsID) {
        this.dshsID = dshsID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public void setBorrowedDate(String borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceLable(String deviceLable) {
        this.deviceLable = deviceLable;
    }

    public void setInventType(String inventType) {
        InventType = inventType;
    }

    public String getDshsID() {
        return dshsID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public String getBorrowedDate() {
        return borrowedDate;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceLable() {
        return deviceLable;
    }

    public String getInventType() {
        return InventType;
    }
}
