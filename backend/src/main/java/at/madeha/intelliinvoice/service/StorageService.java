package at.madeha.intelliinvoice.service;

import java.io.InputStream;

/**
 * Generic blueprint for file storage operations.
 * Decouples the business logic from specific providers (like AWS, Google Cloud, or Local),
 * allowing the storage implementation to be swapped without changing the service layer.
 */
public interface StorageService {

    /**
     * Uploads a file stream to the configured cloud provider.
     *
     * @param fileInput   The binary data of the file.
     * @param fileName    The name to be used for the stored object.
     * @param contentType The MIME type (image/jpeg) for cloud metadata.
     * @return The public or signed URL of the uploaded file.
     */
    String uploadFile(InputStream fileInput, String fileName, String contentType);
}