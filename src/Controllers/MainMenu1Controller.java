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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainMenu1Controller implements Initializable {

    @FXML
    Button getInfo;

    @FXML
    private Button buttArtklRueckgb;

    @FXML
    private Button buttArtklHnzfgn;

    @FXML
    private Button buttAusleihliste;

    @FXML
    private Button buttAvailableItems;

    @FXML
    private Button buttInvHinzufugen;

    @FXML
    private Button buttAllDev;

    @FXML
    private AnchorPane holderPane;



    AnchorPane artikelRckgbe;
    AnchorPane artikelAuslhn;
    AnchorPane artikelHnzfgn;
    AnchorPane ausleihListe;
    AnchorPane availbleItems;
    AnchorPane invHinzufgn;
    AnchorPane alleArtikel;
    AnchorPane searchDev;
    AnchorPane searchId;



    private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node)node);
    }

    public void switchToArtikelAusleihen(ActionEvent event) throws IOException, RuntimeException {
        artikelAuslhn = FXMLLoader.load(getClass().getResource("../FXML/artikelAusleihen.fxml"));
        setNode(artikelAuslhn);
    }

    public void switchToArtikelRueckgabe(ActionEvent event) throws IOException {
        artikelRckgbe = FXMLLoader.load(getClass().getResource("../FXML/artikelRueckgabe.fxml"));
        setNode(artikelRckgbe);
    }

    public void switchToArtikelHinzufgn(ActionEvent event) throws IOException {
        artikelHnzfgn = FXMLLoader.load(getClass().getResource("../FXML/artikelHinzufgn.fxml"));
        setNode(artikelHnzfgn);
    }

    public void switchToAusleihliste(ActionEvent event) throws IOException {
        ausleihListe = FXMLLoader.load(getClass().getResource("../FXML/ausleihListe.fxml"));
        setNode(ausleihListe);
    }

    public void switchToAvailableItems(ActionEvent event) throws IOException {
        availbleItems = FXMLLoader.load(getClass().getResource("../FXML/availableItems.fxml"));
        setNode(availbleItems);
    }

    public void switchToInventHinzufgn(ActionEvent event) throws IOException {
        invHinzufgn = FXMLLoader.load(getClass().getResource("../FXML/inventArtkl.fxml"));
        setNode(invHinzufgn);
    }

    public void switchToAlleArtikel(ActionEvent event) throws IOException {
        alleArtikel = FXMLLoader.load(getClass().getResource("../FXML/alleArtikel.fxml"));
        setNode(alleArtikel);
    }

    public void switchToSearchDev(ActionEvent event) throws IOException {
        searchDev = FXMLLoader.load(getClass().getResource("../FXML/searchDev.fxml"));
        setNode(searchDev);
    }

    public void switchToSearchId(ActionEvent event) throws IOException {
        searchId = FXMLLoader.load(getClass().getResource("../FXML/searchID.fxml"));
        setNode(searchId);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
