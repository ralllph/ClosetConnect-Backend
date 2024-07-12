package com.capstone.closetconnect.configs.amazon_s3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    //create client
    @Bean
    public S3Client s3Client(){
        return S3Client.builder()
                .region(Region.US_EAST_1)
                .build();
    }
}
