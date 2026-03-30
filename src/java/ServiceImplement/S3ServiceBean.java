package ServiceImplement;

import java.io.InputStream;
import java.net.URI;
import java.util.UUID;
import javax.ejb.Stateless;
import service.S3Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;


@Stateless
public class S3ServiceBean implements S3Service {

    private static final String AWS_ACCESS_KEY_ID = "YOUR_AWS_ACCESS_KEY_ID";
    private static final String AWS_SECRET_ACCESS_KEY = "YOUR_AWS_SECRET_ACCESS_KEY";
    private static final String S3_BUCKET_NAME = "your-melodymedia-bucket";
    private static final Regions S3_REGION = Regions.AP_SOUTHEAST_1;
    private static final String POST_IMAGES_FOLDER = "posts/";

    private String generateUniqueKey(String originalFileName) {
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        return POST_IMAGES_FOLDER + UUID.randomUUID().toString() + extension;
    }

    private String extractKeyFromUrl(String imageUrl) {
        try {
            URI uri = new URI(imageUrl);
            String path = uri.getPath();
            String[] parts = path.split("/", 3);
            return parts.length >= 3 ? parts[2] : path.substring(1);
        } catch (Exception e) {
            int postsIdx = imageUrl.indexOf(POST_IMAGES_FOLDER);
            return postsIdx >= 0 ? imageUrl.substring(postsIdx) : imageUrl;
        }
    }

    private AmazonS3 buildS3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(S3_REGION)
                .build();
    }

    @Override
    public String uploadImage(InputStream inputStream, String fileName, String contentType, long fileSize) {
        AmazonS3 s3Client = buildS3Client();
        String s3Key = generateUniqueKey(fileName);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(fileSize);

        PutObjectRequest putRequest = new PutObjectRequest(
                S3_BUCKET_NAME, s3Key, inputStream, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);

        s3Client.putObject(putRequest);

        return s3Client.getUrl(S3_BUCKET_NAME, s3Key).toString();
    }

    @Override
    public void deleteImage(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return;
        }
        AmazonS3 s3Client = buildS3Client();
        String s3Key = extractKeyFromUrl(imageUrl);
        s3Client.deleteObject(new DeleteObjectRequest(S3_BUCKET_NAME, s3Key));
    }
}
