package jp.co.eatfirst.backendapi.app.service;

import com.google.common.io.Files;
import jp.co.eatfirst.backendapi.middleware.aws.S3Manager;
import jp.co.eatfirst.backendapi.middleware.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@Slf4j
public class FileUploadService {

    @Autowired
    S3Manager s3;


    public String uploadImage2Cloud(String path, MultipartFile file) {
        try {
//            String originalFilename = file.getOriginalFilename();
//            String fileExtension = Files.getFileExtension(originalFilename);
//            ByteArrayOutputStream thumbnailFile = new ByteArrayOutputStream();
//            net.coobird.thumbnailator.Thumbnails.of(file.getInputStream()).size(300, 300).outputFormat(fileExtension).toOutputStream(thumbnailFile);

            return s3.uploadFileToS3bucket(path, file);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("Image.Error");
        }

    }

}
