package com.kookdonge.kookdonge_server.common;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "cloud.aws")
public class AwsS3Property {

    @NotBlank
    private String region;

    @NestedConfigurationProperty
    private S3 s3;

    @NestedConfigurationProperty
    private Credentials credentials;

    @Data
    public static class S3 {
        @NotBlank
        private String bucket;
    }

    @Data
    public static class Credentials {
        @NotBlank
        private String accessKey;

        @NotBlank
        private String secretKey;
    }
}
