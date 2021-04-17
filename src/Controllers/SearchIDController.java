package Controllers;

import DBConnection.DatabaseConnection;
import application.SearchIdIntermediate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SearchIDController implements Initializable {

    @FXML
    private TextField dshsIdBox;

    @FXML
    private Button buttSearchID;

    @FXML
    private Label report;

    @FXML
    private TableView<SearchIdIntermediate> dshsSuchenTableView;

    @FXML
    private TableColumn<SearchIdIntermediate, String> dshsID_col;

    @FXML
    private TableColumn<SearchIdIntermediate, String> vName_col;

    @FXML
    private TableColumn<SearchIdIntermediate, String> nName_Col;

    @FXML
    private TableColumn<SearchIdIntermediate, Integer> artikelID_col;

    @FXML
    private TableColumn<SearchIdIntermediate, String> datum_col;

    @FXML
    private TableColumn<SearchIdIntermediate, String> artklName_col;

    @FXML
    private TableColumn<SearchIdIntermediate, String> etikett_col;

    @FXML
    private TableColumn<SearchIdIntermediate, String> invntTyp_col;

    ObservableList<SearchIdIntermediate> oblist = FXCollections.observableArrayList();


    private static String dshsID;


    //
    public void searchDshsID(ActionEvent event) throws SQLException {

        dshsID = dshsIdBox.getText();

        if (!application.Persons.checkDshsIDAvailablity(dshsID)) {
            report.setText("Invalid DSHS-ID! Bitte geben Sie die gültige DSHS-ID ein!");
            report.setTextFill(Color.web("cd0000"));
            if (dshsIdBox.getText()=="") {
                report.setText("Textfield kann nicht leer sein! Bitte geben Sie die gültige DSHS-ID ein!");
                report.setTextFill(Color.web("cd0000"));}
        } else {

            PreparedStatement statement = null;
            try {
                statement = DatabaseConnection.conn.prepareStatement("SELECT  person.dshsID, person.firstName, person.lastName,borroweditem.fk_deviceID, borroweditem.borrowDate, device.deviceName, device.labelName, inventory.inventType FROM person JOIN borroweditem ON person.personID = borroweditem.fk_personID JOIN device ON device.deviceID = borroweditem.fk_deviceID JOIN inventory ON inventory.inventID = borroweditem.fk_inventID WHERE person.dshsID = '" + dshsID + "'");

                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    oblist.add(new SearchIdIntermediate(result.getString("dshsID"), result.getString("firstName"), result.getString("lastName"), Integer.parseInt(result.getString("fk_deviceID")), result.getString("borrowDate"), result.getString("deviceName"), result.getString("labelName"), result.getString("inventType")));
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            dshsID_col.setCellValueFactory(new PropertyValueFactory<>("dshsID"));
            vName_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            nName_Col.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            artikelID_col.setCellValueFactory(new PropertyValueFactory<>("deviceId"));
            datum_col.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
            artklName_col.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
            etikett_col.setCellValueFactory(new PropertyValueFactory<>("deviceLable"));
            invntTyp_col.setCellValueFactory(new PropertyValueFactory<>("inventType"));

            dshsSuchenTableView.setItems(oblist);
            report.setText("Valid DSHS-ID.");
            report.setTextFill(Color.web("74e513"));

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
