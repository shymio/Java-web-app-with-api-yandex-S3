package com.example.crudpractice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class YandexS3Service {
    private final S3Client s3Client;

    public String uploadPhoto(MultipartFile file) throws IOException {
        String key = "photos/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        try {
            PutObjectResponse response = s3Client.putObject(
                    PutObjectRequest.builder()
                    .bucket("shymio-bucket")
                    .key(key)
                    .contentType(file.getContentType())
                    .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
            return "https://" + "shymio-bucket" + ".storage.yandexcloud.net/" + key;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки файла в Yandex Object Storage", e);
        }
    }
}
