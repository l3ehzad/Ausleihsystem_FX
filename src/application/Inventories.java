package application;
import DBConnection.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class Inventories {            //to check nr. of availablity
    /*    private static int inventID;*/
    private String inventType;
    private int inventSum;


    //CONSTRUCTOR:
    public Inventories(String inventType, int inventSum) {
/*        try {
            inventID = setInventID();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        this.inventType = inventType;
        this.inventSum = inventSum;
    }

    public String getInventType() {
        return inventType;
    }

    public int getInventSum() {
        return inventSum;
    }

    public static boolean checkInventIdAvailablity(int inventID) throws SQLException{
        ArrayList<Integer> array = new ArrayList<Integer>();
        PreparedStatement statement = DatabaseConnection.conn.prepareStatement("SELECT inventID FROM inventory;");
        ResultSet result = statement.executeQuery();
        while (result.next()){
            array.add(result.getInt(1));
        }
        return (array.contains(inventID));
    }

    public static boolean checkInvTypeAva (String inventType) throws SQLException {
        ArrayList<String> array = new ArrayList<>();
        PreparedStatement statement = DatabaseConnection.conn.prepareStatement("SELECT inventType FROM inventory;");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            array.add(resultSet.getString(1));
        }
        return (array.contains(inventType));
    }

    //SQL Befehl zum INSERT Inventory
    public static void postInventory (Inventories inventories) throws Exception{

        PreparedStatement posted = DatabaseConnection.conn.prepareStatement("INSERT INTO inventory (inventType, inventNumber) VALUES ('"+ inventories.getInventType()+"', '"+ inventories.getInventSum()+"')");
        posted.executeUpdate();}

    //Increase number of inventory items +1
    public static void incInvNr(int invID) throws SQLException {
        PreparedStatement statement = DatabaseConnection.conn.prepareStatement("UPDATE inventory SET inventNumber = inventNumber + 1 WHERE inventID = "+invID);
        statement.executeUpdate();
    }


    @Override
    public String toString() {
        return "Inventory{" +
                "inventType='" + inventType + '\'' +
                ", inventSum=" + inventSum +
                /*                ", inventID=" + inventID +*/
                '}';
    }
}
