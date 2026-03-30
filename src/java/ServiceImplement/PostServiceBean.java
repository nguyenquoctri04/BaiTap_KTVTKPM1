package ServiceImplement;

import DTO.PostDTO;
import Entity.Post;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import service.PostService;
import service.S3Service;

@Stateless
public class PostServiceBean implements PostService {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private S3Service s3Service;

    @Override
    public PostDTO createTextPost(String userId, String content) {
        validateUserId(userId);
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Nội dung bài đăng không được để trống.");
        }

        Post post = new Post();
        post.setUserId(userId);
        post.setContent(content.trim());

        em.persist(post);
        em.flush();
        return PostDTO.fromEntity(post);
    }

    @Override
    public PostDTO createImagePost(String userId, List<ImageInput> imageInputs) {
        validateUserId(userId);
        validateImageInputs(imageInputs);

        List<String> imageUrls = uploadImages(imageInputs);

        Post post = new Post();
        post.setUserId(userId);
        post.setImageUrls(imageUrls);

        em.persist(post);
        em.flush();
        return PostDTO.fromEntity(post);
    }

    @Override
    public PostDTO createTextImagePost(String userId, String content, List<ImageInput> imageInputs) {
        validateUserId(userId);
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Nội dung bài đăng không được để trống.");
        }
        validateImageInputs(imageInputs);

        List<String> imageUrls = uploadImages(imageInputs);

        Post post = new Post();
        post.setUserId(userId);
        post.setContent(content.trim());
        post.setImageUrls(imageUrls);

        em.persist(post);
        em.flush();
        return PostDTO.fromEntity(post);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = em.createNamedQuery("Post.findAll", Post.class).getResultList();
        return toPostDTOList(posts);
    }

    @Override
    public List<PostDTO> getPostsByUser(String userId) {
        validateUserId(userId);
        List<Post> posts = em.createNamedQuery("Post.findByUserId", Post.class)
                .setParameter("userId", userId)
                .getResultList();
        return toPostDTOList(posts);
    }

    @Override
    public PostDTO getPostById(Long postId) {
        if (postId == null) throw new IllegalArgumentException("postId không được null.");
        Post post = findActivePost(postId);
        return PostDTO.fromEntity(post);
    }

    @Override
    public PostDTO editPostContent(Long postId, String userId, String content) {
        validateUserId(userId);
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Nội dung mới không được để trống.");
        }

        Post post = findActivePost(postId);
        verifyOwnership(post, userId);

        post.setContent(content.trim());
        em.merge(post);
        return PostDTO.fromEntity(post);
    }

    @Override
    public void deletePost(Long postId, String userId) {
        validateUserId(userId);

        Post post = findActivePost(postId);
        verifyOwnership(post, userId);

        if (post.hasImages()) {
            for (String imageUrl : post.getImageUrls()) {
                try {
                    s3Service.deleteImage(imageUrl);
                } catch (Exception e) {
                    System.err.println("[PostServiceBean] Không thể xóa ảnh S3: " + imageUrl + " - " + e.getMessage());
                }
            }
        }

        post.setDeleted(true);
        em.merge(post);
    }

    private List<String> uploadImages(List<ImageInput> imageInputs) {
        List<String> imageUrls = new ArrayList<String>();
        for (ImageInput img : imageInputs) {
            String url = s3Service.uploadImage(
                    img.getInputStream(),
                    img.getFileName(),
                    img.getContentType(),
                    img.getFileSize()
            );
            imageUrls.add(url);
        }
        return imageUrls;
    }

    private Post findActivePost(Long postId) {
        Post post = em.find(Post.class, postId);
        if (post == null || post.isDeleted()) {
            throw new IllegalArgumentException("Bài đăng với ID " + postId + " không tồn tại.");
        }
        return post;
    }

    private void verifyOwnership(Post post, String userId) {
        if (!post.getUserId().equals(userId)) {
            throw new SecurityException("Người dùng " + userId + " không có quyền thao tác trên bài đăng này.");
        }
    }

    private void validateUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("userId không được để trống.");
        }
    }

    private void validateImageInputs(List<ImageInput> imageInputs) {
        if (imageInputs == null || imageInputs.isEmpty()) {
            throw new IllegalArgumentException("Danh sách ảnh không được để trống.");
        }
    }

    private List<PostDTO> toPostDTOList(List<Post> posts) {
        List<PostDTO> result = new ArrayList<>();
        if (posts != null) {
            for (Post p : posts) {
                result.add(PostDTO.fromEntity(p));
            }
        }
        return result;
    }
}
