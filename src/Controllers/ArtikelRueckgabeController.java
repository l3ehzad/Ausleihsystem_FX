package Controllers;

import DBConnection.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ArtikelRueckgabeController implements Initializable {

    @FXML
    private TextField DevIdChecker;

    @FXML
    private Button buttDevInfo;

    @FXML
    private Button buttRueckgabe;

    @FXML
    private Label report;

    @FXML
    private Label report2;

    @FXML
    private Label report3;

    @FXML
    private Label report4;

    @FXML
    private Label report5;

    static boolean legal = false;
    static int devId = -1;


    public void devIdChecker() {

        if (DevIdChecker.getText() == "") {
            report.setText("Eingabe ist Leer. Bitte geben Sie die Gerät ID ein.");
        }
        try {
            devId = Integer.parseInt(DevIdChecker.getText());
            if (!application.BorrowedItems.checkDevAvailInBorrList(devId)) {
                report.setText("Invalid Input: Die Eingabe ist nicht gültig oder der Artikel ist nicht in der Ausleihliste vorhanden.");
                report2.setText("");
                report3.setText("");
                report4.setText("");
                report5.setText("");
            } else {
                getDeviceInfoByDevID(devId);
                legal = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NumberFormatException e){
            report2.setText("Invalid Input: Sie müssen ein Zahl als Gerät ID eingeben!");
        }
    }

    private void getDeviceInfoByDevID(int deviceID) throws SQLException {
        int availablity = 0;
        PreparedStatement statement = DatabaseConnection.conn.prepareStatement("SELECT * FROM device WHERE deviceID = "+ deviceID + ";");
        ResultSet result = statement.executeQuery();
        while (result.next()){
            availablity = result.getInt("available");
            report2.setText("Gerät-ID: " + deviceID);
            report3.setText("Gerät-Name: "+ result.getString(2) + " - "+ result.getString(3));
            if (availablity == 0) report.setText("Status: Available in Ausleihliste.");
            else report.setText("Status: Unavailable in Ausleihliste.");
            System.out.println();
        }
    }

    public void confirmRueckgb () throws SQLException {
        if (legal == true) {
            application.BorrowedItems.deleteDeviceFromBorr(devId);
            application.BorrowedItems.changeToAvailable(devId);
            report5.setText("Gerät wurde erfolgreich von der Ausleihliste gelöscht.");
        } else {
            report5.setText("Gerät-Info sind nicht korrekt. Bitte geben Sie Gerät ID nochmal ein und klicken Sie auf Gerät Info Taste zuerst.");}
    }



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }
}
