package service;

import java.io.InputStream;
import javax.ejb.Local;

@Local
public interface S3Service {

    String uploadImage(InputStream inputStream, String fileName, String contentType, long fileSize);

    void deleteImage(String imageUrl);
}
