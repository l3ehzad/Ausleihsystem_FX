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
}
