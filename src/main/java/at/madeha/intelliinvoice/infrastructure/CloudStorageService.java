
package at.madeha.intelliinvoice.infrastructure;


import at.madeha.intelliinvoice.service.StorageService;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.resteasy.reactive.multipart.FileUpload;


@ApplicationScoped
//so that Quarkus can create the object for this class using the ID concept .
public class CloudStorageService implements StorageService {


    @Override
    public String uploadFile(FileUpload uploadInvoice) {
        // write the  cloud upload logic later on
        return "https://cloud.example.com/" + uploadInvoice.fileName(); // placeholder
    }
}
