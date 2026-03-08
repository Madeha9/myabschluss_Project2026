
package at.madeha.intelliinvoice.service;


import java.io.InputStream;


/*
 * Defines methods for file storage
 */
public interface StorageService {

    // a Method to upload the file to the cloud
    String uploadFile(InputStream fileInput, String fileName);
}