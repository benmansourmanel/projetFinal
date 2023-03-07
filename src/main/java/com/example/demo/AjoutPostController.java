/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import com.example.demo.interfaces.PostService;

import java.io.File;
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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.services.PostImp;

/**
 * FXML Controller class
 *
 * @author Manel
 */
public class AjoutPostController implements Initializable {

    @FXML
    private TextArea content;
    @FXML
    private Button PublierButton;
    @FXML
    private Button PrecedentButton;
    private PostService PostService=new PostImp();
    @FXML
    private Button uploadImageButton;
    @FXML
    private ImageView imageView;
    @FXML
    private Label controleSaisieLabel;

    private String imageUrl="";
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controleSaisieLabel.setVisible(false);

        // TODO
    }    

    @FXML
    private void PublierAction(MouseEvent event) {
        if (content.getText().equals("")) {
            controleSaisieLabel.setVisible(true);
            controleSaisieLabel.setText("content shouldn't be empty !!");
        }else{
            controleSaisieLabel.setVisible(false);

            Post p =new Post();
            p.setContent(content.getText());
            p.setImageUrl(imageUrl);
            p.setUser(new User(1));
            PostService.addPost(p);
            try {
                Stage stage;
                Parent signUpPage = FXMLLoader.load(getClass().getResource("ListandDetailsPublications.fxml"));
                // borderpane.setCenter(signUpPage);
                Scene scene = new Scene(signUpPage);
                stage = (Stage)PublierButton.getScene().getWindow();
                stage.hide();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                //Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        return;
        }


    @FXML
    private void PrecedentAction(MouseEvent event) {
        try {
             Stage stage;
        Parent signUpPage = FXMLLoader.load(getClass().getResource("AfficherPost.fxml"));
        // borderpane.setCenter(signUpPage);
        Scene scene = new Scene(signUpPage);
        stage = (Stage)PrecedentButton.getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
            //Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void handleUploadButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            imageUrl=selectedFile.toURI().toString();
        }
    }
}
