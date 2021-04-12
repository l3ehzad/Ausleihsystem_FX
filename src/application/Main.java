package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/login.fxml"));
            Scene scene = new Scene(root, 1024, 600);

            //String css = this.getClass().getResource("styling.css").toExternalForm();
            //scene.getStylesheets().add(css);

            //define scene 2 for css 2 - main menu:
/*            Scene scene2 = new Scene(FXMLLoader.load(getClass().getResource("mainMenu1.fxml")));
            scene2.getStylesheets().add(css);*/

            Image icon = new Image("/zImages/icon.png");
            stage.getIcons().add(icon);
            stage.setTitle("  Deutsche Sporthochschule Köln | Ausleihsystem");
            stage.setScene(scene);
            stage.setResizable(false);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
