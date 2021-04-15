package Controllers;

import application.BorrowedItems;
import application.DateStamps;
import application.Persons;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class artikelAusleihenController implements Initializable {

    @FXML
    private VBox menuBox;

    @FXML
    private Label testLabel;

    @FXML
    private Button butt01;

    @FXML
    private Pane pane_aa;

    @FXML
    private TextField lastName;

    @FXML
    private TextField firstName;

    @FXML
    private TextField dshsID;

    @FXML
    private TextField deviceID;

    @FXML
    private TextField reason;

    @FXML
    private Button auslButt;

    @FXML
    private Label auslReport;




    public void ausleihenHandler() throws Exception {
        String borrowDate = DateStamps.printTime();

        String lName = lastName.getText();
        String fName = firstName.getText();
        String dsID = dshsID.getText();
        String reas = reason.getText();
        int devID = Integer.parseInt(deviceID.getText());

        Persons persons = new Persons(dsID, lName, fName);
        persons.addPersonToSQL(dsID);

        BorrowedItems borrowedItems = new BorrowedItems(devID, dsID, borrowDate, reas);
        borrowedItems.addBorrowedItemToSQL();

        auslReport.setText("Status: Artikel wurde erfolgreich hinzugefügt.");
        auslReport.setTextFill(Color.web("74e513"));
        lastName.setText("");
        firstName.setText("");
        dshsID.setText("");
        reason.setText("");
        deviceID.setText("");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
