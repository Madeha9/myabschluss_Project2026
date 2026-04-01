package at.madeha.intelliinvoice.infrastructure;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Configuration class to establish a secure connection to AWS S3.
 * Uses Environment Variables to keep sensitive API keys out of the source code.
 */
public class S3ClientConfig {
    /**
     * Creates and configures the AWS S3 Client.
     *
     * @return A ready-to-use S3Client instance.
     * @throws RuntimeException if AWS credentials are missing from the system.
     */
    public static S3Client createS3Client() {
// Pulling credentials from the system environment for security
        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");

        if (accessKey == null || secretKey == null) {
            throw new RuntimeException("AWS credentials not set in environment variables");
        }
// Creating the credentials object
        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(accessKey, secretKey);
// Building the client for the specific Northern Europe region
        return S3Client.builder()
                .region(Region.EU_NORTH_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(credentials))
                .build();
    }
}