/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author joybo
 */
import java.util.List;
import javax.ejb.Local;
import Entity.Comments;

@Local
public interface CommentService {

    void addComment(String postId, String userId, String text);
        
    void editComment(String postId, String userId, String text);
    
    void deleteComment(String postId, Long commentId);

    List<Comments> getCommentsByPost(String postId);
    
    
}