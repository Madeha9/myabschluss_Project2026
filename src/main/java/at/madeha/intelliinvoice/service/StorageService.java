
package at.madeha.intelliinvoice.service;


import org.jboss.resteasy.reactive.multipart.FileUpload;


/*
 * Defines methods for file storage
 */
public interface StorageService {
    String uploadFile(FileUpload uploadInvoice);


}