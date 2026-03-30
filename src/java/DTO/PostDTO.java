package DTO;

import Entity.Post;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long postId;
    private String userId;
    private String content;
    private List<String> imageUrls;
    private String postType;
    private Date createdAt;
    private Date updatedAt;

    public PostDTO() {
        this.imageUrls = new ArrayList<>();
    }

    public static PostDTO fromEntity(Post post) {
        if (post == null) return null;

        PostDTO dto = new PostDTO();
        dto.setPostId(post.getPostId());
        dto.setUserId(post.getUserId());
        dto.setContent(post.getContent());
        dto.setImageUrls(post.getImageUrls() != null ? post.getImageUrls() : new ArrayList<String>());
        dto.setPostType(post.getPostType() != null ? post.getPostType().name() : null);
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        return dto;
    }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

    public String getPostType() { return postType; }
    public void setPostType(String postType) { this.postType = postType; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
