package at.madeha.intelliinvoice.infrastructure;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class S3ClientConfig {

    public static S3Client createS3Client() {

        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");

        if (accessKey == null || secretKey == null) {
            throw new RuntimeException("AWS credentials not set in environment variables");
        }

        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client.builder()
                .region(Region.EU_NORTH_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(credentials))
                .build();
    }
}