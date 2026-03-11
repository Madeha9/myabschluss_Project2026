package at.madeha.intelliinvoice.service.helper;

import at.madeha.intelliinvoice.infrastructure.CloudStorageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.InputStream;

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

    public String processUploadedInvoice(InputStream fileInput, String fileName) {

        // upload image to S3
        String imageUrl = cloudStorageService.uploadFile(fileInput, fileName);

        return imageUrl;
    }
}