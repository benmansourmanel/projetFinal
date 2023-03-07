/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.interfaces;

import com.example.demo.models.Like;

import java.util.List;

/**
 *
 * @author Manel
 */
public interface LikeInterface {
  public void addLike(Like like);
  public List<Like> getLikesByIdPost(int idPost); 
  public void dislike(Like like);
  public void deleteLike(Like like);
}
