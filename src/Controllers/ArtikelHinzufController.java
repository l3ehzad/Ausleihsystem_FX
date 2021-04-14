package Controllers;

import application.Devices;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

public class ArtikelHinzufController implements Initializable {

    @FXML
    private TextField artName;

    @FXML
    private TextField etkt;

    @FXML
    private TextField invId;

    @FXML
    private Button buttHinzufugn;

    @FXML
    private Label report;

    @FXML
    private Label report2;

    AnchorPane artikelHnzfgn;

    String deviceName = "";
    String labelName = "";
    int inventID;




    public void addDevice(ActionEvent event) throws Exception {
        try {
            deviceName = artName.getText();
            labelName = etkt.getText();
            inventID = Integer.parseInt(invId.getText());
            if(!application.Inventories.checkInventIdAvailablity(inventID)) {
                report.setText("");
                report2.setText("Status: Invalid Input! Geben Sie gültige Inventar-ID für Gerät Kategorie.");}
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NumberFormatException e){
            report2.setText("Bitte geben Sie eine gültige Zahl als Inventar-ID.");
            report2.setTextFill(Color.web("cd0000"));
        }

        if (artName.getText()!="" && etkt.getText() != ""){
            application.Devices device1 = new Devices(deviceName, labelName, inventID);
            application.Devices.postDevice(device1);
            application.Inventories.incInvNr(inventID);
            report.setText("");
            report2.setText("Gerät wurde erfolgreich hinzugefügt!");
            report2.setTextFill(Color.web("74e513"));

            artName.setText("");
            etkt.setText("");
            invId.setText("");
        } else {
            report.setText("Text-Fields dürfen nicht leer sein. Wenn nicht vorhanden, geben sie bitte \"-\" ein.");
            report.setTextFill(Color.web("cd0000"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
