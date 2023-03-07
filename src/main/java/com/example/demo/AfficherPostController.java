/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Manel
 */
public class AfficherPostController implements Initializable {

    @FXML
    private Label Publicaton;
    @FXML
    private AnchorPane Accueil;
    @FXML
    private ListView<String> MyListView;
    @FXML
    private Label NomProduitLabel;
    @FXML
    private Button ajouterPublicationButton;
    @FXML
    private Button publicationListViewButton;
    @FXML
    private Button connectLikeClient;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void Accueil(MouseEvent event) {
    }




    @FXML
    private void MyIListViewOpened(KeyEvent event) {
    }

    @FXML
    private void MyAjouterPublicationInterfaceOpened(MouseEvent event) {
       try {
             Stage stage;
        Parent signUpPage = FXMLLoader.load(getClass().getResource("AjoutPost.fxml"));
        // borderpane.setCenter(signUpPage);
        Scene scene = new Scene(signUpPage);
        stage = (Stage)ajouterPublicationButton.getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
        }
    }
    private void showView(Stage primaryStage,String fileXmlName,String title ){
         
    }

    @FXML
    private void publicationListViewAction(MouseEvent event) {
        try {
             Stage stage;
        Parent signUpPage = FXMLLoader.load(getClass().getResource("ListandDetailsPublications.fxml"));
        // borderpane.setCenter(signUpPage);
        Scene scene = new Scene(signUpPage);
        stage = (Stage)ajouterPublicationButton.getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
        }
    }
    @FXML
    void connectLikeClientHundler(ActionEvent event) {
        try {
            Stage stage;
            Parent signUpPage = FXMLLoader.load(getClass().getResource("CommenterPub.fxml"));
            // borderpane.setCenter(signUpPage);
            Scene scene = new Scene(signUpPage);
            stage = (Stage)connectLikeClient.getScene().getWindow();
            stage.hide();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
        }
    }
}
