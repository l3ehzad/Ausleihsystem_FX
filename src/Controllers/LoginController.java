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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    //Stage stage;

    @FXML
    private TextField logUsername;

    @FXML
    private PasswordField logPassword;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;



    public void handleLoginAction(ActionEvent event) throws SQLException, IOException {
        String username = logUsername.getText().toString();
        String password = logPassword.getText().toString();
        DatabaseConnection databaseConnection = new DatabaseConnection(username, password);
        if (databaseConnection.checkValidity()) {
            switchToMainMenu(event);
        } else {
            if (databaseConnection.checkValidity() == false) {
                loginLabel.setText("Incorrect Username or Password!");
                loginLabel.setTextFill(Color.web("#ff0000"));
            }
        }
    }

    public void switchToMainMenu(ActionEvent event) throws IOException, RuntimeException {
        root = FXMLLoader.load(getClass().getResource("../FXML/mainMenu1.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
