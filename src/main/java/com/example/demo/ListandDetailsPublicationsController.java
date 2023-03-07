/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import com.example.demo.Gui.NewFXMain;
import com.example.demo.interfaces.PostService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.example.demo.util.PipeLine;
import com.example.demo.util.TextToSpeechSample;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.demo.models.Post;
import com.example.demo.services.PostImp;

/**
 * FXML Controller class
 *
 * @author Manel
 */
public class ListandDetailsPublicationsController implements Initializable {

    @FXML
    private AnchorPane detailAnchorPane;
    @FXML
    private Label Content;
    @FXML
    private Button PrecedentButton;
    @FXML
    private Button ModifierButton;
    @FXML
    private Button SupprimerButton;
    @FXML
    private AnchorPane listPublicationAnchorPane;
    @FXML
    private ListView<String> listPublications;
    @FXML
    private ImageView imageView;
    /**
     * Initializes the controller class.
     */
    private PostService postService = new PostImp();
    private Post selectedPost;
    private List<Post> postsList;
    @FXML
    private Label imageUrl;
    @FXML
    private AnchorPane ModifierAnchorPane;
    @FXML
    private TextArea content;
    @FXML
    private Button modifierButton;
    @FXML
    private Button voiceButton;
    @FXML
    private Label detailsNLPLabel;
    @FXML
    private Label loadingLabel;
    @FXML
    private ImageView updateImageViewer;

    @FXML
    private Button uplodeImageButton;
    @FXML
    private Label controleSaisieLabel;
    private String image_Url="";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadingLabel.setVisible(false);
        controleSaisieLabel.setVisible(false);

        listPublicationAnchorPane.setVisible(true);
        ModifierAnchorPane.setVisible(false);
        detailAnchorPane.setVisible(false);
        postsList = postService.getPostByIdUser(1);
        List<String> itemsList = postsList.stream().map(p -> p.getContent()).collect(Collectors.toList());
        ;
        listPublications.getItems().addAll(itemsList);
        listPublications.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listPublications.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
        // TODO
    }

    private void selectionChanged(ObservableValue<? extends String> observable, String oldVal, String newVal) {
        try {
            ObservableList<String> selectedItems = listPublications.getSelectionModel().getSelectedItems();
            int selectedItemsIndex = listPublications.getSelectionModel().getSelectedIndex();
            selectedPost = postsList.get(selectedItemsIndex);
            String getSelectionItem = (selectedItems.isEmpty()) ? "no item " : selectedItems.toString();
            System.out.println(selectedPost.toString());
            afficherDetails(selectedPost);
        } catch (Exception e) {
        }

    }


    @FXML
    private void PrecedentAction(MouseEvent event) {
        listPublicationAnchorPane.setVisible(true);
        detailAnchorPane.setVisible(false);
        ModifierAnchorPane.setVisible(false);

    }

    @FXML
    private void ButtonModifierClicked(ActionEvent event) {
        content.setText(selectedPost.getContent());
        listPublicationAnchorPane.setVisible(false);
        ModifierAnchorPane.setVisible(true);
        detailAnchorPane.setVisible(false);
    }

    @FXML
    private void buttonSupprimerClicked(ActionEvent event) {
        listPublications.getItems().clear();
        postService.deletePost(selectedPost.getId());
        ModifierAnchorPane.setVisible(false);

        listPublicationAnchorPane.setVisible(true);
        detailAnchorPane.setVisible(false);
        postsList = postService.getPostByIdUser(1);
        List<String> itemsList = postsList.stream().map(p -> p.getContent()).collect(Collectors.toList());
        ;
        listPublications.getItems().addAll(itemsList);
    }

    private void afficherDetails(Post p) {
        loadingLabel.setVisible(true);


        Content.setText(p.getContent());
        ModifierAnchorPane.setVisible(false);
        listPublicationAnchorPane.setVisible(false);
        detailAnchorPane.setVisible(true);
        loadingLabel.setVisible(false);
        Image image = new Image(p.getImageUrl());
        imageView.setImage(image);


        String show = "";
        StanfordCoreNLP stanfordCoreNLP = PipeLine.getPipeline();
        CoreDocument coreDocument = new CoreDocument(p.getContent());
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreLabel> sentences = coreDocument.tokens();
        for (CoreLabel coreLabel : sentences) {
            System.out.println(coreLabel.originalText());

            show += coreLabel.originalText() + " = " + coreLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class) + "\n";

        }
        System.out.println(show);
        loadingLabel.setVisible(false);

        detailsNLPLabel.setText(show);
    }

    @FXML
    private void modifierAction(ActionEvent event) {
        if (content.getText().equals("")) {
            controleSaisieLabel.setVisible(true);
            controleSaisieLabel.setText("content shouldn't be empty !!");

        }else{
            controleSaisieLabel.setVisible(false);

            selectedPost.setContent(content.getText());
            selectedPost.setImageUrl(image_Url);

            postService.updatePost(selectedPost);
            listPublications.getItems().clear();
            ModifierAnchorPane.setVisible(false);

            listPublicationAnchorPane.setVisible(true);
            detailAnchorPane.setVisible(false);
            postsList = postService.getPostByIdUser(1);
            List<String> itemsList = postsList.stream().map(p -> p.getContent()).collect(Collectors.toList());
            listPublications.getItems().addAll(itemsList);
        }

    }

    @FXML
    void readContentAction(ActionEvent event) {
        TextToSpeechSample.textToSpeech(selectedPost.getContent(), selectedPost.getId() + "");
        voiceButton.setVisible(false);

        String filePath = "file:///C:/Users/ksghaier/Desktop/demo/src/main/resources/mp3Files/"+selectedPost.getId()+".mp3";
        Media media = new Media(filePath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaPlayer.play();
        voiceButton.setVisible(true);

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
            updateImageViewer.setImage(image);
            image_Url=selectedFile.toURI().toString();
        }
    }
    @FXML
    void goBackHomeHundler(ActionEvent event) {
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
        }
    }
}