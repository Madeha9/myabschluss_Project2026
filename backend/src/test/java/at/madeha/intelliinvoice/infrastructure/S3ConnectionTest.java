//package at.madeha.intelliinvoice.infrastructure;
//
/// *
//Class to test the connection to S3
//*/
//
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
//
//public class S3ConnectionTest {
//
//    static void main(String[] args) {
//
//        // Read credentials from environment variables
//        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
//        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
//        if (accessKey == null || secretKey == null) {
//            throw new RuntimeException("AWS credentials not set in environment variables");
//        }
//        // Create AWS credentials
//        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
//
//        // Create S3 client
//        S3Client s3 = S3Client.builder()
//                .region(Region.EU_NORTH_1)
//                .credentialsProvider(StaticCredentialsProvider.create(credentials))
//                .build();
//
//        System.out.println("S3 client created successfully!");
//
//        // List buckets to test connection
//        ListBucketsResponse buckets = s3.listBuckets();
//
//        buckets.buckets().forEach(bucket ->
//                System.out.println("Bucket name: " + bucket.name())
//        );
//    }
//}