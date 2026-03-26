package at.madeha.intelliinvoice.infrastructure;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

public class S3ConnectionTest {

    static void main(String[] args) {

        S3Client s3 = S3ClientConfig.createS3Client();

        System.out.println("S3 client created successfully!");

        ListBucketsResponse buckets = s3.listBuckets();

        buckets.buckets().forEach(bucket ->
                System.out.println("Bucket name: " + bucket.name())
        );
    }
}
