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
    @Inject
    AWSConfig config;
    private S3Client s3Client;

    //Constructor
      /*fileupload.filename to get the original name of the uploaded file i which is a unique file name in (Key)
        in S3 to avoid many files with same names, s3 uses the key to save the file or to return them
         */
    @PostConstruct
    void init() {
        s3Client = S3Client.builder()
                .region(Region.of(config.getRegion()))
                .build();
    }


    @Override
    /*
    @uploadFile to upload the file
    * FileUpload is a class in the Quarkus for the file that the user upload them using API controllers it contains
    Infos like file name and size etc...
    @param fileUpload is the uploaded file object
     */
    public String uploadFile(InputStream fileInput, String fileName, String contentType) {
        String key = UUID.randomUUID() + "_" + fileName;
//Update the signature to accept contentType

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(config.getBucketName()) //(bucketName)
                    .key(key).contentType(contentType)
                    .build();

            // Upload to S3 directly from InputStream
            s3Client.putObject(request, RequestBody.fromInputStream(fileInput, fileInput.available()));
            String urlImage = "https://" + config.getBucketName() + ".s3." + config.getRegion() + ".amazonaws.com/" + key;
            return urlImage;

        } catch (IOException e) {
            throw new FileUploadException("Failed to read uploaded file", e);
        } catch (Exception e) {
            throw new FileUploadException("Failed to upload file to S3", e);
        }
    }
}



