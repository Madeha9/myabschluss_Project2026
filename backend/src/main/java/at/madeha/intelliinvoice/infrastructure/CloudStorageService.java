package at.madeha.intelliinvoice.infrastructure;
import at.madeha.intelliinvoice.config.AWSConfig;
import at.madeha.intelliinvoice.exception.FileUploadException;
import at.madeha.intelliinvoice.service.StorageService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Implementation of the StorageService using AWS S3.
 * Handles the actual transfer of file data from the application to the Amazon cloud.
 */
@ApplicationScoped
public class CloudStorageService implements StorageService {
    @Inject
    AWSConfig config;
    /**
     * S3Client is the official AWS SDK class used to interact with S3
     * (upload, download, delete files, etc.).
     */
    private S3Client s3Client;

    /**
     * Initializes the S3 client after the class is constructed.
     * Uses the 'Builder Pattern' to configure the connection.
     */
      /*fileupload.filename to get the original name of the uploaded file in which is a unique file name in (Key)
        in S3 to avoid many files with same names, s3 uses the key to save the file or to return them
         */

    @PostConstruct
    void init() {
        /* * Builder pattern is like building a sandwich:
         * You add the ingredients (Region, Credentials, etc.) one by one,
         * and the final 'build()' step gives you the finished object.
         */
        s3Client = S3Client.builder()
                .region(Region.of(config.getRegion()))
                .build();
    }

    /**
     * Uploads an invoice file to the S3 bucket.
     * @param fileInput The stream of data from the uploaded file.
     * @param fileName The original name of the file.
     * @param contentType The MIME type (e.g., image/jpeg or application/pdf).
     * @return The public URL of the uploaded image.
     */
    @Override
    /*
    FileUpload is a class in the Quarkus for the file that the user upload them using API controllers it contains
    Infos like file name and size etc...fileUpload is the uploaded file object
     */
    public String uploadFile(InputStream fileInput, String fileName, String contentType) {
        /* * We create a 'Key' (unique filename) using a UUID + the original name.
         * This prevents files from being overwritten if two users upload 'invoice.pdf'.
         */
        String key = UUID.randomUUID() + "_" + fileName;
        try {
            // Prepare the  (Request) for AWS
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(config.getBucketName()) //(bucketName)
                    .key(key).contentType(contentType)
                    .build();
// Perform the actual upload using the InputStream
            // Upload to S3 directly from InputStream
            s3Client.putObject(request, RequestBody.fromInputStream(fileInput, fileInput.available()));
            // Construct the final URL so the Frontend can display the image
            String urlImage = "https://" + config.getBucketName() + ".s3." + config.getRegion() + ".amazonaws.com/" + key;
            return urlImage;

        } catch (IOException e) {
            // Specific error for when we can't read the file data
            throw new FileUploadException("Failed to read uploaded file", e);
        } catch (Exception e) {
            // General error for connection issues with AWS
            throw new FileUploadException("Failed to upload file to S3", e);
        }
    }
}



