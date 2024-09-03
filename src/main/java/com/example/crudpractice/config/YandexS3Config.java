package com.example.crudpractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class YandexS3Config {

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of("ru-central1"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("YCAJEaaBK76Yja0BjF_RFmpai", "YCNo3ozcjIpUOhTWfkKOZGSHDi-Za1Ga9eK4lfSK")))
                .endpointOverride(URI.create("https://storage.yandexcloud.net"))  
                .build();
    }
}
