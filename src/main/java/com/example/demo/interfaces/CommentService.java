/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.interfaces;

import com.example.demo.models.Comment;

import java.util.List;

/**
 *
 * @author Manel
 */
public interface CommentService {
  public void addComment(Comment comment);
  public List<Comment> getCommentByIdPost(int idPost); 
  public void updateComment(Comment comment);
  public void deleteComment(int idComment);

}
