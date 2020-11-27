package jp.co.eatfirst.backendapi.middleware.aws;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.PostConstruct;

import jp.co.eatfirst.backendapi.middleware.IdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.internal.Mimetypes;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import jp.co.eatfirst.backendapi.middleware.exception.BusinessException;

@Component
public class S3Manager {

    private AmazonS3 s3client;

    @Value("${aws.S3.region}")
    private String region;
    @Value("${aws.S3.bucket}")
    private String bucketName;
    @Value("${aws.access-key-id}")
    private String accessKey;
    @Value("${aws.access-key-secret}")
    private String secretKey;

    private static final MimetypesFileTypeMap FILE_TYPE = new MimetypesFileTypeMap();

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region)).withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }

    public String uploadFileToS3bucket(String filePath, MultipartFile multipartFile) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            uploadFileToS3bucket(filePath, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("https://%s.s3-%s.amazonaws.com/", bucketName, region) + filePath;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(IdGenerator.next(IdGenerator.IdType.DEFAULT) + file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private void uploadFileToS3bucket(String filePath, File file) {

        String fileType = FILE_TYPE.getContentType(filePath.substring(filePath.lastIndexOf("/") + 1));
        if (!Mimetypes.MIMETYPE_OCTET_STREAM.equals(fileType)) {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(fileType);
            s3client.putObject(new PutObjectRequest(bucketName, filePath, file).withCannedAcl(CannedAccessControlList.PublicRead).withMetadata(meta));
        } else {
            throw new BusinessException("file type is error");
        }

    }

    public void deleteFileFromS3Bucket(String filePath) {
        s3client.deleteObject(new DeleteObjectRequest(bucketName, filePath));
    }

    public List<String> listFiles() {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName).withPrefix("/");

        List<String> keys = new ArrayList<>();
        ObjectListing objects = s3client.listObjects(listObjectsRequest);
        while (true) {
            List<S3ObjectSummary> summaries = objects.getObjectSummaries();
            if (summaries.size() < 1) {
                break;
            }
            for (S3ObjectSummary item : summaries) {
                if (!item.getKey().endsWith("/"))
                    keys.add(item.getKey());
            }

            objects = s3client.listNextBatchOfObjects(objects);
        }
        return keys;
    }
}