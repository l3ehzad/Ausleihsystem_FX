package Controllers;

import DBConnection.DatabaseConnection;
import application.AlleArtikelIntermediate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class AlleArtikelController implements Initializable {

    @FXML
    private TableView<AlleArtikelIntermediate> alleArtiklTableView;

    @FXML
    private TableColumn<AlleArtikelIntermediate, Integer> artID_col;

    @FXML
    private TableColumn<AlleArtikelIntermediate, String> artName_col;

    @FXML
    private TableColumn<AlleArtikelIntermediate, String> etktName_col;

    @FXML
    private TableColumn<AlleArtikelIntermediate, Integer> availble_col;

    @FXML
    private TableColumn<AlleArtikelIntermediate, Integer> invID_col;

    ObservableList<AlleArtikelIntermediate> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PreparedStatement statement = null;
        try {
            statement = DatabaseConnection.conn.prepareStatement("SELECT * FROM device");

            ResultSet result = statement.executeQuery();
            while (result.next()){
                oblist.add(new AlleArtikelIntermediate(Integer.parseInt(result.getString("deviceID")),result.getString("deviceName"), result.getString("labelName"), Integer.parseInt(result.getString("available")), Integer.parseInt(result.getString("fk_inventID"))));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        artID_col.setCellValueFactory(new PropertyValueFactory<>("devID"));
        artName_col.setCellValueFactory(new PropertyValueFactory<>("devName"));
        etktName_col.setCellValueFactory(new PropertyValueFactory<>("lableName"));
        availble_col.setCellValueFactory(new PropertyValueFactory<>("available"));
        invID_col.setCellValueFactory(new PropertyValueFactory<>("invID"));

        alleArtiklTableView.setItems(oblist);



    }
}
