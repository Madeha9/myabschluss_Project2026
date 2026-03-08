package at.madeha.intelliinvoice.infrastructure;

import at.madeha.intelliinvoice.exception.FileUploadException;
import at.madeha.intelliinvoice.service.StorageService;
import jakarta.enterprise.context.ApplicationScoped;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@ApplicationScoped
public class CloudStorageService implements StorageService {
    /*
     * S3Client is class in AWS SDK to interact with the S3(upload , download, delete file etc... )
     *  AWS sdk use builder pattern to create it instead of creating it with new S3Client
     * builder we use it to configure the s3 like setting the parameter
     * .region(Region.of(region)) to transform the region name from String to AWS object so that he know to which
     * datacenter he is tailing to then build the S3 object using the method build()
     * builder pattern is a design pattern where to use to build object very fast without using the construction
     * like the last step in building the sandwich
     */
    private final S3Client s3Client;
    private final String bucketName = "intelliinvoice-files-wifi-2026";
    private final String region = "eu-north-1";
    //need to update later on
//    S3Client s3 = S3ClientConfig.createS3Client();

    //Constructor
      /*fileupload.filename to get the original name of the uploaded file i which is a unique file name in (Key)
        in S3 to avoid many files with same names, s3 uses the key to save the file or to return them
         */
    public CloudStorageService() {
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .build();
    }
    @Override
    /*
    @uploadFile to upload the file
    * FileUpload is a class in the Quarkus for the file that the user upload them using API controllers it contains
    Infos like file name and size etc...
    @param fileUpload is the uploaded file object
     */
    public String uploadFile(InputStream fileInput, String fileName) {
        String key = UUID.randomUUID() + "_" + fileName;

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            // Upload to S3 directly from InputStream
            s3Client.putObject(request, RequestBody.fromInputStream(fileInput, fileInput.available()));

            return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;

        } catch (IOException e) {
            throw new FileUploadException("Failed to read uploaded file", e);
        } catch (Exception e) {
            throw new FileUploadException("Failed to upload file to S3", e);
        }
    }
}



