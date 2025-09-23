package com.kookdonge.kookdonge_server.common.property;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "cloud.aws")
@Validated
public class AwsS3Property {

    @NotBlank
    private String region;

    @NestedConfigurationProperty
    @Valid
    private S3 s3;

    @NestedConfigurationProperty
    @Valid
    private Credentials credentials;

    @Getter
    @Setter
    public static class S3 {
        @NotBlank
        private String bucket;
    }

    @Getter
    @Setter
    public static class Credentials {
        @NotBlank
        private String accessKey;

        @NotBlank
        private String secretKey;
    }
}
