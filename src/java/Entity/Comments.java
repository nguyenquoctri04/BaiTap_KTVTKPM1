package Entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comments implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String postId;
    private String userID;
    private String text;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Instant createAt;

    public Comments() {
    }

    public Comments(String postId, String userID, String text) {
        this.postId = postId;
        this.userID = userID;
        this.text = text;
        this.createAt = Instant.now();
    }

    // ===== Getter & Setter =====

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userID;
    }

    public void setUserId(String userID) {
        this.userID = userID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public Instant getCreateAt(){
        return createAt;
    }
}