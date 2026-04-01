package at.madeha.intelliinvoice.service.helper;
import at.madeha.intelliinvoice.infrastructure.CloudStorageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.InputStream;

/**
 * Internal helper for managing file uploads.
 * Acts as a bridge between the InvoiceProcessingService and the CloudStorageService.
 */
/*
 * the uplaod controller recives the file from the user , call the service layer , the service layer call the storage layer
 * to save the file and return the url, the url is n ow in the service layer to save it in the tadabase , and sent it ot the
 * LLM to recive a json from the LLm , and save the json file in the database
 */
@ApplicationScoped
public class InvoiceUploadService {
    //    StorageService storageService;

    @Inject
    CloudStorageService cloudStorageService;

    /**
     * Forwards the file stream to the cloud storage layer.
     * It handles the handshake with the infrastructure layer to securely
     * store the physical file and return its unique URL.
     *
     * @return The accessible URL of the uploaded file.
     */
    public String processUploadedInvoice(InputStream fileInput, String fileName, String contentType) {

// Triggers the actual upload to S3 via the infrastructure service
        String imageUrl = cloudStorageService.uploadFile(fileInput, fileName, contentType);
        return imageUrl;
    }
}