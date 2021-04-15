package Controllers;

import DBConnection.DatabaseConnection;
import application.InventArtkIntermediate;
import application.Inventories;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class InventArtklController implements Initializable {


    @FXML
    private TableView<InventArtkIntermediate> inventTableView;

    @FXML
    private TableColumn<InventArtkIntermediate, Integer> invID_col;

    @FXML
    private TableColumn<InventArtkIntermediate, String> invName_col;

    @FXML
    private TableColumn<InventArtkIntermediate, Integer> menge_col;

    @FXML
    private TextField newInventInput;

    @FXML
    private Button buttAddInvent;

    @FXML
    private Label report;

    ObservableList<application.InventArtkIntermediate> oblist = FXCollections.observableArrayList();

    public void addInventButton (ActionEvent event) throws Exception {
        String invType=null;
        int inventSum=0;

        if (newInventInput.getText()==""){
            report.setText("Status: Fehler! Text Field kann nicht leer sein.");
            report.setTextFill(Color.web("cd0000"));
        } else {
            invType = newInventInput.getText().toLowerCase();
            if (!Inventories.checkInvTypeAva(invType)) {
                Inventories inventory = new Inventories(invType, inventSum);
                Inventories.postInventory(inventory);
                report.setText("Inventar Kategorie: "+invType+ ",wurde erfolgreich hinzugefügt.");
                report.setTextFill(Color.web("74e513"));
                newInventInput.setText("");


            } else {
                report.setText("Inventar Kategorie: "+invType+ ",ist schon vorhanden. Bitte geben Sie neue Kategorie ein.");
                report.setTextFill(Color.web("cd0000"));

            }
            }
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PreparedStatement statement = null;

        //it gets all the MySQL info and puts them into an arrayList (here ist called ObservableList) which we call it for instance: oblist
        try {
            statement = DatabaseConnection.conn.prepareStatement("SELECT * FROM `inventory`");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                oblist.add(new InventArtkIntermediate(Integer.parseInt(rs.getString("inventID")), rs.getString("inventType"), Integer.parseInt(rs.getString("inventNumber"))));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //now putting the context of the observableList into the real column we made through "intermediate class"
        invID_col.setCellValueFactory(new PropertyValueFactory<>("inventId"));
        invName_col.setCellValueFactory(new PropertyValueFactory<>("invTyp"));
        menge_col.setCellValueFactory(new PropertyValueFactory<>("invNumber"));

        inventTableView.setItems(oblist);


    }
}
