package com.capstone.closetconnect.services.amazon_s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectAclRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    //upload image to bucket
    public void putObject(String bucketName, String key, byte[] imageBytes){
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        //invoke s3 client to perform upload
        s3Client.putObject(objectRequest, RequestBody.fromBytes(imageBytes));
    }

    //get image back
    public byte[] getObject(String bucketName, String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest
                .builder()
                .bucket(bucketName)
                .key(key)
                .build();

        //retrieve response object
        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(getObjectRequest);

        //extract image bytes
        try {
            return response.readAllBytes();
        } catch (IOException e) {
            log.info("unable to retreive image from s3 bucket {} with key{}",bucketName,key);
            throw new RuntimeException(e);
        }
    }


}
