package Controllers;


import DBConnection.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AvailableItemsController implements Initializable {

    @FXML
    private TableView<application.AvailableItemsIntermediate> availableTableView;

    @FXML
    private TableColumn<application.AvailableItemsIntermediate, Integer> devID_col;

    @FXML
    private TableColumn<application.AvailableItemsIntermediate, String> devName_col;

    @FXML
    private TableColumn<application.AvailableItemsIntermediate, String> devLable_col;

    ObservableList<application.AvailableItemsIntermediate> oblist = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PreparedStatement statement = null;

        //it gets all the MySQL info and puts them into an arrayList (here ist called ObservableList) which we call it for instance: oblist
        try {
            statement = DatabaseConnection.conn.prepareStatement("SELECT * FROM `device` WHERE device.available = 1");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                oblist.add(new AvailableItemsIntermediate(Integer.parseInt(rs.getString("deviceID")), rs.getString("deviceName"), rs.getString("labelName")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //now putting the context of the observableList into the real column we made through "intermediate class"
        devID_col.setCellValueFactory(new PropertyValueFactory<>("devID"));
        devName_col.setCellValueFactory(new PropertyValueFactory<>("devName"));
        devLable_col.setCellValueFactory(new PropertyValueFactory<>("devLable"));

        availableTableView.setItems(oblist);


    }
}
