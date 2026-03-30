package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "posts")
@NamedQueries({
    @NamedQuery(name = "Post.findAll",
            query = "SELECT p FROM Post p ORDER BY p.createdAt DESC"),
    @NamedQuery(name = "Post.findByUserId",
            query = "SELECT p FROM Post p WHERE p.userId = :userId ORDER BY p.createdAt DESC"),
    @NamedQuery(name = "Post.findByPostId",
            query = "SELECT p FROM Post p WHERE p.postId = :postId")
})
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "post_images", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type", nullable = false)
    private PostType postType;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "is_deleted")
    private boolean deleted = false;

    public enum PostType {
        TEXT_ONLY,
        IMAGE_ONLY,
        TEXT_IMAGE
    }

    public Post() {
    }

    public Post(String userId, String content, List<String> imageUrls) {
        this.userId = userId;
        this.content = content;
        this.imageUrls = (imageUrls != null) ? imageUrls : new ArrayList<>();
        this.createdAt = new Date();
        this.postType = determinePostType();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        this.postType = determinePostType();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
        this.postType = determinePostType();
    }

    private PostType determinePostType() {
        boolean hasContent = content != null && !content.trim().isEmpty();
        boolean hasImages = imageUrls != null && !imageUrls.isEmpty();

        if (hasContent && hasImages) return PostType.TEXT_IMAGE;
        if (hasImages) return PostType.IMAGE_ONLY;
        return PostType.TEXT_ONLY;
    }

    public boolean hasImages() {
        return imageUrls != null && !imageUrls.isEmpty();
    }

    public boolean hasContent() {
        return content != null && !content.trim().isEmpty();
    }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

    public PostType getPostType() { return postType; }
    public void setPostType(PostType postType) { this.postType = postType; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}
