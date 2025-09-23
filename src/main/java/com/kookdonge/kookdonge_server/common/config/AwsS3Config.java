package com.kookdonge.kookdonge_server.common.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.kookdonge.kookdonge_server.common.AwsS3Property;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AwsS3Config {

    private final AwsS3Property awsS3Property;

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                awsS3Property.getCredentials().getAccessKey(),
                awsS3Property.getCredentials().getSecretKey());
        return new AWSStaticCredentialsProvider(awsCredentials);
    }

    @Bean
    public AmazonS3Client amazonS3Client() {
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(awsS3Property.getRegion())
                .withCredentials(awsCredentialsProvider())
                .build();
    }
}
