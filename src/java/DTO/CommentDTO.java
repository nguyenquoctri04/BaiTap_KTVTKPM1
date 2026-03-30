package DTO;

import Entity.Comment;
import java.io.Serializable;
import java.util.Date;

public class CommentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long commentId;
    private Long postId;
    private String userId;
    private String text;
    private Date createdAt;
    private Date updatedAt;

    public CommentDTO() {
    }

    public static CommentDTO fromEntity(Comment comment) {
        if (comment == null) return null;

        CommentDTO dto = new CommentDTO();
        dto.setCommentId(comment.getCommentId());
        dto.setPostId(comment.getPostId());
        dto.setUserId(comment.getUserId());
        dto.setText(comment.getText());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        return dto;
    }

    // Getters and Setters
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}