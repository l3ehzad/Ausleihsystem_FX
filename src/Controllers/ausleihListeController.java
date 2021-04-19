package Controllers;

import DBConnection.DatabaseConnection;
import application.BorrowedItemsIntermediate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ausleihListeController implements Initializable {


    @FXML
    private TableView<application.BorrowedItemsIntermediate> ausleiListeTableView;

    @FXML
    private TableColumn<application.BorrowedItemsIntermediate, Integer> devIdCol;

    @FXML
    private TableColumn<application.BorrowedItemsIntermediate, String> devNameCol;

    @FXML
    private TableColumn<application.BorrowedItemsIntermediate, String> devLabelCol;

    @FXML
    private TableColumn<application.BorrowedItemsIntermediate, String> dshsIdCol;

    @FXML
    private TableColumn<application.BorrowedItemsIntermediate, String> nameCol;

    @FXML
    private TableColumn<application.BorrowedItemsIntermediate, String> borrDateCol;

    @FXML
    private TableColumn<application.BorrowedItemsIntermediate, String> reasonCol;

    @FXML
    private Button ButtAusleihPrint;

    ObservableList<BorrowedItemsIntermediate> oblist = FXCollections.observableArrayList();

    public void printAusleihList(ActionEvent event){
        PrinterJob printerJob = PrinterJob.createPrinterJob();


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PreparedStatement statement = null;
        try {
            statement = DatabaseConnection.conn.prepareStatement("SELECT device.deviceID, device.deviceName, device.labelName, inventory.inventType, person.dshsID, person.firstName, person.lastName, borroweditem.borrowDate, borroweditem.reason FROM device JOIN borroweditem ON device.deviceID = borroweditem.fk_deviceID JOIN inventory ON inventory.inventID = borroweditem.fk_inventID JOIN person ON borroweditem.fk_personID = person.personID");

        ResultSet result = statement.executeQuery();
        while (result.next()){
            oblist.add(new application.BorrowedItemsIntermediate(Integer.parseInt(result.getString("deviceID")),result.getString("deviceName"), result.getString("labelName"),result.getString("dshsID"), result.getString("lastName"), result.getString("borrowDate"),result.getString("reason")));
        }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        devIdCol.setCellValueFactory(new PropertyValueFactory<>("devID"));
        devNameCol.setCellValueFactory(new PropertyValueFactory<>("devName"));
        devLabelCol.setCellValueFactory(new PropertyValueFactory<>("devEtiket"));
        dshsIdCol.setCellValueFactory(new PropertyValueFactory<>("dshsID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        borrDateCol.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));

        ausleiListeTableView.setItems(oblist);



    }
}
