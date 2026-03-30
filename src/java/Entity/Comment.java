package Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "comments")
@NamedQueries({
    @NamedQuery(name = "Comment.findAll",
            query = "SELECT c FROM Comment c ORDER BY c.createdAt DESC"),
    @NamedQuery(name = "Comment.findByPostId",
            query = "SELECT c FROM Comment c WHERE c.postId = :postId ORDER BY c.createdAt DESC"),
    @NamedQuery(name = "Comment.findByUserId",
            query = "SELECT c FROM Comment c WHERE c.userId = :userId ORDER BY c.createdAt DESC"),
    @NamedQuery(name = "Comment.findByCommentId",
            query = "SELECT c FROM Comment c WHERE c.commentId = :commentId")
})
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "text", columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "is_deleted")
    private boolean deleted = false;

    public Comment() {
    }

    public Comment(Long postId, String userId, String text) {
        this.postId = postId;
        this.userId = userId;
        this.text = text;
        this.createdAt = new Date();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}