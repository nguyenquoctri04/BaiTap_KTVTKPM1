package ServiceImplement;

import DTO.CommentsDTO;
import Entity.Comments;
import Entity.Post;
import service.CommentService;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.*;

@Stateless
public class CommentServiceBean implements CommentService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public CommentsDTO addComment(Long postId, String userId, String text) {
        validatePostId(postId);
        validateUserId(userId);
        validateCommentText(text);

        // Kiểm tra post tồn tại và chưa bị xóa
        Post post = findActivePost(postId);

        Comments comment = new Comments();
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setText(text.trim());

        em.persist(comment);
        em.flush();
        return CommentsDTO.fromEntity(comment);
    }

    @Override
    public CommentsDTO editComment(Long commentId, String userId, String text) {
        validateCommentId(commentId);
        validateUserId(userId);
        validateCommentText(text);

        Comments comment = findActiveComment(commentId);
        verifyOwnership(comment, userId);

        comment.setText(text.trim());
        em.merge(comment);
        return CommentsDTO.fromEntity(comment);
    }

    @Override
    public void deleteComment(Long commentId, String userId) {
        validateCommentId(commentId);
        validateUserId(userId);

        Comments comment = findActiveComment(commentId);
        verifyOwnership(comment, userId);

        comment.setDeleted(true);
        em.merge(comment);
    }

    @Override
    public List<CommentsDTO> getCommentsByPost(Long postId) {
        validatePostId(postId);
        
        // Kiểm tra post tồn tại
        findActivePost(postId);

        List<Comments> comments = em.createNamedQuery("Comments.findByPostId", Comments.class)
                .setParameter("postId", postId)
                .getResultList();

        return toCommentsDTOList(comments);
    }

    @Override
    public CommentsDTO getCommentById(Long commentId) {
        validateCommentId(commentId);
        Comments comment = findActiveComment(commentId);
        return CommentsDTO.fromEntity(comment);
    }

    private Comments findActiveComment(Long commentId) {
        Comments comment = em.find(Comments.class, commentId);
        if (comment == null || comment.isDeleted()) {
            throw new IllegalArgumentException("Bình luận với ID " + commentId + " không tồn tại.");
        }
        return comment;
    }

    private Post findActivePost(Long postId) {
        Post post = em.find(Post.class, postId);
        if (post == null || post.isDeleted()) {
            throw new IllegalArgumentException("Bài đăng với ID " + postId + " không tồn tại.");
        }
        return post;
    }

    private void verifyOwnership(Comments comment, String userId) {
        if (!comment.getUserId().equals(userId)) {
            throw new SecurityException("Người dùng " + userId + " không có quyền thao tác trên bình luận này.");
        }
    }

    private void validateCommentId(Long commentId) {
        if (commentId == null) {
            throw new IllegalArgumentException("commentId không được null.");
        }
    }

    private void validatePostId(Long postId) {
        if (postId == null) {
            throw new IllegalArgumentException("postId không được null.");
        }
    }

    private void validateUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("userId không được để trống.");
        }
    }

    private void validateCommentText(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Nội dung bình luận không được để trống.");
        }
    }

    private List<CommentsDTO> toCommentsDTOList(List<Comments> comments) {
        List<CommentsDTO> result = new ArrayList<>();
        if (comments != null) {
            for (Comments c : comments) {
                if (!c.isDeleted()) {
                    result.add(CommentsDTO.fromEntity(c));
                }
            }
        }
        return result;
    }
}