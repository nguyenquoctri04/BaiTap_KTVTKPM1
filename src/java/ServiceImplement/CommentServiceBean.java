/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServiceImplement;

/**
 *
 * @author joybo
 */
import Entity.Comments;
import service.CommentService;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.*;

@Stateless
public class CommentServiceBean implements CommentService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addComment(String postId, String userId, String text) {
        Comments c = new Comments();
        c.setPostId(postId);
        c.setUserId(userId);
        c.setText(text);
        c.setCreatedAt(new Date());

        em.persist(c);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comments c = em.find(Comments.class, commentId);
        if (c != null) {
            em.remove(c);
        }
    }

    @Override
    public List<Comments> getCommentsByPost(String postId) {
        return em.createQuery(
                "SELECT c FROM Comment c WHERE c.postId = :postId",
                Comments.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    @Override
    public void editComment(String postId, String userId, String text) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteComment(String postId, Long commentId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
