package application;

public class BorrowedItemsIntermediate {

    private int devID;
    private String devName, devEtiket, dshsID, name, borrowDate, reason;

    public BorrowedItemsIntermediate(int devID, String devName, String devEtiket, String dshsID, String name, String borrowDate, String reason) {
        this.devID = devID;
        this.devName = devName;
        this.devEtiket = devEtiket;
        this.dshsID = dshsID;
        this.name = name;
        this.borrowDate = borrowDate;
        this.reason = reason;
    }

    public void setDevID(int devID) {
        this.devID = devID;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public void setDevEtiket(String devEtiket) {
        this.devEtiket = devEtiket;
    }

    public void setDshsID(String dshsID) {
        this.dshsID = dshsID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getDevID() {
        return devID;
    }

    public String getDevName() {
        return devName;
    }

    public String getDevEtiket() {
        return devEtiket;
    }

    public String getDshsID() {
        return dshsID;
    }

    public String getName() {
        return name;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getReason() {
        return reason;
    }
}
