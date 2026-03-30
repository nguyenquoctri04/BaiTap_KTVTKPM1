package service;

import DTO.CommentDTO;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface CommentService {

    CommentDTO addComment(Long postId, String userId, String text);

    CommentDTO editComment(Long commentId, String userId, String text);

    void deleteComment(Long commentId, String userId);

    List<CommentDTO> getCommentsByPost(Long postId);

    CommentDTO getCommentById(Long commentId);
}