package service;

import DTO.PostDTO;
import java.io.InputStream;
import java.util.List;
import javax.ejb.Remote;

@Remote 
public interface PostService {

    PostDTO createTextPost(String userId, String content);

    PostDTO createImagePost(String userId, List<ImageInput> imageInputs);

    PostDTO createTextImagePost(String userId, String content, List<ImageInput> imageInputs);

    List<PostDTO> getAllPosts();

    List<PostDTO> getPostsByUser(String userId);

    PostDTO getPostById(Long postId);

    PostDTO editPostContent(Long postId, String userId, String content);

    void deletePost(Long postId, String userId);

    class ImageInput {
        private final InputStream inputStream;
        private final String fileName;
        private final String contentType;
        private final long fileSize;

        public ImageInput(InputStream inputStream, String fileName, String contentType, long fileSize) {
            this.inputStream = inputStream;
            this.fileName = fileName;
            this.contentType = contentType;
            this.fileSize = fileSize;
        }

        public InputStream getInputStream() { return inputStream; }
        public String getFileName() { return fileName; }
        public String getContentType() { return contentType; }
        public long getFileSize() { return fileSize; }
    }
}
