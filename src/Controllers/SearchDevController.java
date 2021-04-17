package Controllers;

import DBConnection.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SearchDevController implements Initializable {

    @FXML
    private TextField devIdBox;

    @FXML
    private Label report;

    @FXML
    private Label report1;

    @FXML
    private Button buttSuchenDev;

    int devId;


    public void searchDevIdButton(ActionEvent event) throws SQLException {
        if (devIdBox.getText()=="") report.setText("TextField kann nicht leer sein. Bitte geben Sie die gültige Artikel-ID ein!");
        devId = Integer.parseInt(devIdBox.getText());
        if (!application.BorrowedItems.checkDeviceIdValidity(devId)) {
            report.setText("Invalid Artikel-ID!");
            report1.setText("");
        } else {
            getDeviceInfoByDevID(devId);
            }
    }

    private void getDeviceInfoByDevID(int deviceID) throws SQLException {
        int availablity = 0;
        PreparedStatement statement = DatabaseConnection.conn.prepareStatement("SELECT * FROM device WHERE deviceID = "+ deviceID + ";");
        ResultSet result = statement.executeQuery();
        while (result.next()){
            availablity = result.getInt("available");
            report.setText("Info für Artikel: " + deviceID +"   |   Device name: "+ result.getString(2) + "  -  "+ result.getString(3)+"   |");
            if (availablity == 0) {
                report1.setText("Verfügbarkeit: unavailable");
                report1.setTextFill(Color.web("cd0000"));
            } else {
                report1.setText("Verfügbarkeit: available");
                report1.setTextFill(Color.web("74e513"));
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
