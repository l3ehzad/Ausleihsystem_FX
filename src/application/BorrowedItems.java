package application;

import DBConnection.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BorrowedItems {

        private int deviceID;           //F.K. to Device class
        private String dshsID;
        private String borrowDate;
        private String reason;

        private int inventID;         //F.K. to Inventory class
        private int personID;


    //CONSTRUCTOR
    public BorrowedItems(int deviceID, String dshsID, String borrowDate, String reason) throws SQLException {

        this.deviceID = deviceID;
        this.dshsID = dshsID;
        this.borrowDate = borrowDate;
        this.reason = reason;

        inventID = getInventIDFromDeviceID(deviceID);
        /*setInventID(getInventIDFromDeviceID(getDeviceID())); */       /*I just removed this from here*/
        personID = getPersonIDFromDshsID(dshsID);
        changeToUnavailable(deviceID);

    }
    //end of CONSTRUCTOR

    public int getDeviceID() {
        return deviceID;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getReason() {
        return reason;
    }

    public int getInventID() {
        return inventID;
    }

    public void setInventID(int inventID) {
        this.inventID = inventID;
    }

    public String getDshsID() {
        return dshsID;
    }

    public int getPersonID(){
        return personID;
    }

    public int getInventIDFromDeviceID(int deviceID) throws SQLException {
        int invID = 0;
        PreparedStatement statement = DatabaseConnection.conn.prepareStatement("SELECT fk_inventID FROM device WHERE deviceID = "+ deviceID+";");
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            invID = result.getInt("fk_inventID");
        }
        else
            System.out.println("NIX DA!");
        return invID;
    }

    public static int getPersonIDFromDshsID(String dshsID) throws SQLException{
        int personID = 0;

        PreparedStatement statement = DatabaseConnection.conn.prepareStatement("SELECT personID FROM person WHERE dshsID = '"+ dshsID+"';");
        ResultSet result = statement.executeQuery();
        if (result.next())
            personID = result.getInt(1);
        else
            personID = 0;

        return personID;
    }

    public void addBorrowedItemToSQL() throws Exception {
        PreparedStatement posted = DatabaseConnection.conn.prepareStatement("INSERT INTO borroweditem ( borrowDate, reason, fk_inventID, fk_deviceID , fk_personID ) VALUES ('"+getBorrowDate()+"', '"+getReason()+"', '"+getInventID()+"', '"+getDeviceID()+"', '"+getPersonID()+"')");
        posted.executeUpdate();}

    public static boolean checkDeviceIdValidity(int deviceID) throws SQLException{
        ArrayList<Integer> array = new ArrayList<Integer>();

        PreparedStatement statement = DatabaseConnection.conn.prepareStatement("SELECT deviceID FROM device;");

        ResultSet result = statement.executeQuery();

        while (result.next()){
            array.add(result.getInt(1));
        }
        return (array.contains(deviceID));
    }

    private static void changeToUnavailable (int deviceID) throws SQLException{
        PreparedStatement posted = DatabaseConnection.conn.prepareStatement("UPDATE device SET available = 0 WHERE deviceID = "+deviceID+";");
        posted.executeUpdate();}

    public static void changeToAvailable (int deviceID) throws SQLException {

        PreparedStatement posted = DatabaseConnection.conn.prepareStatement("UPDATE device SET available = 1 WHERE deviceID = "+deviceID+";");
        posted.executeUpdate();}

    public static boolean checkDevAvailInBorrList(int devID) throws SQLException {
        ArrayList<Integer> array = new ArrayList<Integer>();

        PreparedStatement statement = DatabaseConnection.conn.prepareStatement("SELECT fk_deviceID FROM borroweditem;");

        ResultSet result = statement.executeQuery();

        while (result.next()){
            array.add(result.getInt(1));
        }
        return (array.contains(devID));
    }

    public static void deleteDeviceFromBorr(int devID) throws SQLException {
        PreparedStatement posted = DatabaseConnection.conn.prepareStatement("DELETE FROM `borroweditem` WHERE `borroweditem`.`fk_deviceID` = "+ devID+";");
        posted.executeUpdate();
    }

    public static void deviceInfoBeforeRemove (int devID) throws SQLException {

        String deviceName = null;
        String labelName = null;

        PreparedStatement statement = DatabaseConnection.conn.prepareStatement("SELECT deviceName, labelName FROM device WHERE deviceID = "+ devID+";");
        /*System.out.println("SQL: "+statement);*/
        ResultSet rs1 = statement.executeQuery();
        while (rs1.next()){
            deviceName = rs1.getString(1);
            labelName = rs1.getString(2);
        }
    }

    public static void listOfAllBorrItems() throws SQLException{

        PreparedStatement statement = DatabaseConnection.conn.prepareStatement("SELECT device.deviceID, device.deviceName, device.labelName, inventory.inventType, person.dshsID, person.firstName, person.lastName, borroweditem.borrowDate, borroweditem.reason FROM device JOIN borroweditem ON device.deviceID = borroweditem.fk_deviceID JOIN inventory ON inventory.inventID = borroweditem.fk_inventID JOIN person ON borroweditem.fk_personID = person.personID");

        ResultSet result = statement.executeQuery();
        while(result.next()){
            System.out.print("Device ID: " + result.getString(1));
            System.out.print(" | Device Name: " + result.getString(2)+" - "+result.getString(4));
            System.out.print(" | Device lable: " + result.getString(3));
            System.out.print(" | DSHS-ID: " + result.getString(5));
            System.out.print(" | Name: " + result.getString(6)+" "+result.getString(7));
            System.out.print(" | Borrow Date: " + result.getString(8));
            System.out.print(" | Reason: " + result.getString(9));
            System.out.println();
        }
    }

    public static void listOfBorrItemsBasedOnDshsID(String dshsID) throws SQLException{
        int sum = 0;
        PreparedStatement statement = DatabaseConnection.conn.prepareStatement("SELECT  person.dshsID, person.firstName, person.lastName,borroweditem.fk_deviceID, borroweditem.borrowDate, device.deviceName, device.labelName, inventory.inventType FROM person JOIN borroweditem ON person.personID = borroweditem.fk_personID JOIN device ON device.deviceID = borroweditem.fk_deviceID JOIN inventory ON inventory.inventID = borroweditem.fk_inventID WHERE person.dshsID = '"+dshsID+"'");

        ResultSet result = statement.executeQuery();
        while(result.next()){
            System.out.print("DSHS-ID: "+result.getString(1));
            System.out.print(" | name: "+result.getString(2)+" "+result.getString(3));
            System.out.print(" | device ID: "+result.getString(4));
            System.out.print(" | borrowed item: "+result.getString(6)+" - "+result.getString(7)+ " ("+result.getString(8)+") ");
            System.out.println(" | borrowed since: "+result.getString(5));
            sum++;
        }
        if (sum ==0) {System.out.println("NO borrowed items found for this DSHS-ID.");}
        else {
            System.out.println(sum+" device/s borrowed by this DSHS-ID.");
        }
        System.out.println();
    }

    public static void listOfAvailItems () throws SQLException{
        PreparedStatement statement = DatabaseConnection.conn.prepareStatement("SELECT * FROM `device` WHERE device.available = 1");

        ResultSet result = statement.executeQuery();
        while(result.next()){
            System.out.print("Device ID: " + result.getString(1));
            System.out.print(" | Device info: " + result.getString(2)+" - "+result.getString(3));
            System.out.println();
        }
    }



    @Override
    public String toString() {
        return "BorrowedItem{" +
                "deviceID=" + deviceID +
                ", dshsID='" + dshsID + '\'' +
                ", borrowDate='" + borrowDate + '\'' +
                ", reason='" + reason + '\'' +
                ", inventID=" + inventID +
                ", personID=" + personID +
                '}';
    }

    }
