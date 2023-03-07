/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import com.example.demo.interfaces.CommentService;
import com.example.demo.interfaces.LikeService;
import com.example.demo.interfaces.PostService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import com.example.demo.models.Comment;
import com.example.demo.models.Like;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.services.CommentImp;
import com.example.demo.services.LikeImp;
import com.example.demo.services.PostImp;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Manel
 */
public class CommenterPubController implements Initializable {

    @FXML
    private AnchorPane detailAnchorPane;
    @FXML
    private Label Content;
    @FXML
    private Label imageUrl;
    @FXML
    private Button LikerButton;
    @FXML
    private Button CommenterButton;
    @FXML
    private AnchorPane listPublicationAnchorPane;
    @FXML
    private ListView<String> listPublications;
    @FXML
    private Button PrecedentButton1;
    
    private PostService postService= new PostImp();
     private Post selectedPost;
    private List<Post> postsList;
    private CommentService commentService=new CommentImp();
    private Comment selectedComment;
    private List<Comment> CommentsList;
    @FXML
    private Button PrecedentDetailsButton;
    @FXML
    private ListView<String> commentairesListView;
    @FXML
    private Label ContentCommentaires;
    private LikeService likeService= new LikeImp();
    @FXML
    private Button DisLikeButton;
    @FXML
    private TextField commentaireInput;
    @FXML
    private ImageView imageView;
    @FXML
    private Label controleSaisieLabel;

    /**
     * Initializes the controller class.
     */
     @Override
    public void initialize(URL url, ResourceBundle rb) {
         controleSaisieLabel.setVisible(false);

         listPublicationAnchorPane.setVisible(true);
        detailAnchorPane.setVisible(false);
        initializePost();
    }    
    

    @FXML
    private void PrecedentAction(MouseEvent event) {
        listPublicationAnchorPane.setVisible(true);
        detailAnchorPane.setVisible(false);
    }

    @FXML
    private void ButtonLikerClicked(ActionEvent event) {
       Like like =new Like();
       like.setLiked(true);
       like.setPost(selectedPost);
       like.setUser(new User(1));
       likeService.addLike(like);
       LikerButton.setVisible(false);
       DisLikeButton.setVisible(true);
       
       
    }

    @FXML
    private void buttonCommenterClicked(ActionEvent event) {
        if (commentaireInput.getText().equals("")) {
            controleSaisieLabel.setVisible(true);
            controleSaisieLabel.setText("content shouldn't be empty !!");;
        }else{
            controleSaisieLabel.setVisible(false);

            Comment c=new Comment();
            c.setContent(commentaireInput.getText());
            c.setPost(selectedPost);
            c.setUser(new User(1));
            c.setImageUrl("");
            commentService.addComment(c);
            initializeCommentaires();
        }

        
        
    }
     private void afficherDetails(Post p){
        Content.setText(p.getContent());
         Image image = new Image(p.getImageUrl());
         imageView.setImage(image);
               
         listPublicationAnchorPane.setVisible(false);
        detailAnchorPane.setVisible(true);
    }

    @FXML
    private void PrecedentDetailsAction(MouseEvent event) {
         listPublicationAnchorPane.setVisible(true);
        detailAnchorPane.setVisible(false);
    }
    private void initializePost(){
         postsList=postService.getPostByIdUser(1);
        List<String> itemsList=postsList.stream().map(p->p.getContent()).collect(Collectors.toList());;
        listPublications.getItems().addAll(itemsList);
        listPublications.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listPublications.getSelectionModel().selectedItemProperty().addListener(this::selectionChangedPost);
        // TODO
    }
    private  void selectionChangedPost(ObservableValue<? extends String> observable,String oldVal,String newVal){
        try {
             ObservableList<String> selectedItems=listPublications.getSelectionModel().getSelectedItems();
        int selectedItemsIndex=listPublications.getSelectionModel().getSelectedIndex();
        selectedPost=postsList.get(selectedItemsIndex);
        String getSelectionItem=(selectedItems.isEmpty()) ? "no item ":selectedItems.toString();
        afficherDetails(selectedPost);
        initializeCommentaires();
          boolean isliked=  likeService.getLikeByIdUser(selectedPost.getId(), 1);
           LikerButton.setVisible(!isliked);
       DisLikeButton.setVisible(isliked);
        } catch (Exception e) {
        }
       
    }   
    private void initializeCommentaires(){
   commentairesListView.getItems().clear();;
      CommentsList=commentService.getCommentByIdPost(selectedPost.getId());
        List<String> itemsList=CommentsList.stream().map(p->p.getContent()).collect(Collectors.toList());
        commentairesListView.getItems().addAll(itemsList);
        commentairesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        commentairesListView.getSelectionModel().selectedItemProperty().addListener(this::selectionChangedCommentaires);
        // TODO  
    }
     private  void selectionChangedCommentaires(ObservableValue<? extends String> observable,String oldVal,String newVal){
        try {
             ObservableList<String> selectedItems=commentairesListView.getSelectionModel().getSelectedItems();
        int selectedItemsIndex=commentairesListView.getSelectionModel().getSelectedIndex();
        selectedComment=CommentsList.get(selectedItemsIndex);
        String getSelectionItem=(selectedItems.isEmpty()) ? "no item ":selectedItems.toString();
        ContentCommentaires.setText(selectedComment.getContent());
       // afficherDetails(selectedPost);
        } catch (Exception e) {
        }
       
    }  

    @FXML
    private void ButtonDisLikeClicked(ActionEvent event) {
         Like like =new Like();
       like.setLiked(true);
       like.setPost(selectedPost);
       like.setUser(new User(1));
       likeService.dislike(like);
       LikerButton.setVisible(true);
       DisLikeButton.setVisible(false);
    }
    @FXML
    void goBackHomeHundler(ActionEvent event) {
        try {
            Stage stage;
            Parent signUpPage = FXMLLoader.load(getClass().getResource("AfficherPost.fxml"));
            // borderpane.setCenter(signUpPage);
            Scene scene = new Scene(signUpPage);
            stage = (Stage)PrecedentButton1.getScene().getWindow();
            stage.hide();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
        }
    }
}
