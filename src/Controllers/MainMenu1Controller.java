package Controllers;

import DBConnection.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainMenu1Controller {

    @FXML
    Button getInfo;

    private Stage stage;
    private Scene scene;
    private Parent root;


    //root = FXMLLoader.load(getClass().getResource("mainMenu1.fxml"));

    public void switchToArtikelAusleihen(ActionEvent event) throws IOException, RuntimeException {
        root = FXMLLoader.load(getClass().getResource("../FXML/artikelAusleihen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

/*    public void buttArtklAusleihen (ActionEvent event) throws IOException, RuntimeException {

    }*/

    public void getInformation(ActionEvent event) throws SQLException {

        //DatabaseConnection databaseConnection = new DatabaseConnection("root","");
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
}
