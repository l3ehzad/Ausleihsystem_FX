package application;

import DBConnection.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class Persons {
    private String dshsID;          //P.K.
    private String lastName;
    private String firstName;


    public Persons(String dshsID, String lastName, String firstName) {
        this.dshsID = dshsID;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getDshsID() {
        return dshsID;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void addPersonToSQL(String dshsID) throws Exception{

        if (BorrowedItems.getPersonIDFromDshsID(dshsID) == 0) {
            PreparedStatement posted = DatabaseConnection.conn.prepareStatement("INSERT INTO person (dshsID , lastName, firstName) VALUES ('" + getDshsID() + "', '" + getLastName() + "', '" + getFirstName() + "')");
            posted.executeUpdate();
        }
        else
            System.out.println("Person already exists!");
    }

    public static boolean checkDshsIDAvailablity(String dshsID) throws SQLException {
        ArrayList<String> array = new ArrayList<String>();
        PreparedStatement statement = DatabaseConnection.conn.prepareStatement("SELECT dshsID FROM person;");

        ResultSet result = statement.executeQuery();

        while (result.next()){
            array.add(result.getString("dshsID"));
        }
        return (array.contains(dshsID));
    }

}

