package br.com.routeworker.service;

import br.com.routeworker.service.Interface.IStorageService;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service implements IStorageService {

    private final S3Client s3Client;

    @Value("${app.s3.bucket}")
    private String bucketName;

    @Override
    public String upload(String key, String content, String contentType) {
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(contentType)
                        .build(),
                RequestBody.fromString(content));
        return key;
    }


}