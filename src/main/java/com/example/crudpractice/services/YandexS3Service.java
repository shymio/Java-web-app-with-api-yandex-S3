package com.example.crudpractice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class YandexS3Service {
    private final S3Client s3Client;
    private static final String BUCKET_NAME = "shymio-bucket";

    public String uploadPhoto(MultipartFile file) throws IOException {
        String key = "photos/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        try {
            PutObjectResponse response = s3Client.putObject(
                    PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(key)
                    .contentType(file.getContentType())
                    .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
            return "https://" + BUCKET_NAME + ".storage.yandexcloud.net/" + key;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки файла в Yandex Object Storage", e);
        }
    }

    private String extractKeyFromUrl(String url) {
        return url.substring(url.indexOf("photos/"));
    }

    public void deletePhoto(String photoUrl) {
        try {
            String key = extractKeyFromUrl(photoUrl);
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка удаления файла из Yandex S3", e);
        }
    }
}
