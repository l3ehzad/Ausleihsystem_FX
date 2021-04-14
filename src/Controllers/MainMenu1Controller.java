package Controllers;

import DBConnection.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainMenu1Controller implements Initializable {

    @FXML
    Button getInfo;

    @FXML
    private Button buttArtklRueckgb;

    @FXML
    private Button buttArtklHnzfgn;

    @FXML
    private Button buttAusleihliste;

    @FXML
    private AnchorPane holderPane;



    AnchorPane artikelRckgbe;
    AnchorPane artikelAuslhn;
    AnchorPane artikelHnzfgn;
    AnchorPane ausleihListe;


    //private Stage stage;
    //private Scene scene;
    //private Parent root;


    //root = FXMLLoader.load(getClass().getResource("mainMenu1.fxml"));

    private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node)node);
    }

    public void switchToArtikelAusleihen(ActionEvent event) throws IOException, RuntimeException {
        artikelAuslhn = FXMLLoader.load(getClass().getResource("../FXML/artikelAusleihen.fxml"));
        setNode(artikelAuslhn);
    }

    public void switchToArtikelRueckgabe(ActionEvent event) throws IOException {
        artikelRckgbe = FXMLLoader.load(getClass().getResource("../FXML/artikelRueckgabe.fxml"));
        setNode(artikelRckgbe);
    }

    public void switchToArtikelHinzufgn(ActionEvent event) throws IOException {
        artikelHnzfgn = FXMLLoader.load(getClass().getResource("../FXML/artikelHinzufgn.fxml"));
        setNode(artikelHnzfgn);
    }

    public void switchToAusleihliste(ActionEvent event) throws IOException {
        ausleihListe = FXMLLoader.load(getClass().getResource("../FXML/ausleihListe.fxml"));
        setNode(ausleihListe);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
