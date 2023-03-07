/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.guii;

import com.example.demo.interfaces.PostService;
import com.example.demo.models.Post;
import com.example.demo.services.PostImp;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

    /**
     * Initializes the controller class.
     */
    private PostService postService= new PostImp();
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listPublicationAnchorPane.setVisible(true);
        ModifierAnchorPane.setVisible(false);
        detailAnchorPane.setVisible(false);
        postsList=postService.getPostByIdUser(1);
        List<String> itemsList=postsList.stream().map(p->p.getContent()).collect(Collectors.toList());;
        listPublications.getItems().addAll(itemsList);
        listPublications.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listPublications.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
        // TODO
    }    
    private  void selectionChanged(ObservableValue<? extends String> observable,String oldVal,String newVal){
        try {
             ObservableList<String> selectedItems=listPublications.getSelectionModel().getSelectedItems();
        int selectedItemsIndex=listPublications.getSelectionModel().getSelectedIndex();
        selectedPost=postsList.get(selectedItemsIndex);
        String getSelectionItem=(selectedItems.isEmpty()) ? "no item ":selectedItems.toString();
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
        postsList=postService.getPostByIdUser(1);
        List<String> itemsList=postsList.stream().map(p->p.getContent()).collect(Collectors.toList());;
        listPublications.getItems().addAll(itemsList);
    }
    private void afficherDetails(Post p){
        Content.setText(p.getContent());
        imageUrl.setText(p.getImageUrl());
               
         listPublicationAnchorPane.setVisible(false);
        detailAnchorPane.setVisible(true);
    }

    @FXML
    private void modifierAction(ActionEvent event) {
        selectedPost.setContent(content.getText());
        selectedPost.setImageUrl("images");

        postService.updatePost(selectedPost);
        listPublications.getItems().clear();
        ModifierAnchorPane.setVisible(false);
        
         listPublicationAnchorPane.setVisible(true);
        detailAnchorPane.setVisible(false);
        postsList=postService.getPostByIdUser(1);
        List<String> itemsList=postsList.stream().map(p->p.getContent()).collect(Collectors.toList());;
        listPublications.getItems().addAll(itemsList);
    }
}
