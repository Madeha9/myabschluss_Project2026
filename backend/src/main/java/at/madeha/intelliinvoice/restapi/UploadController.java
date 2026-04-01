package at.madeha.intelliinvoice.restapi;

import at.madeha.intelliinvoice.database.InvoiceEntity;
import at.madeha.intelliinvoice.exception.InvoiceValidationException;
import at.madeha.intelliinvoice.service.InvoiceProcessingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

/**
 * High-speed entry point for file uploads.
 * This controller manages the complex "Multipart" handshake required to
 * move a physical file from the user's computer to our server.
 */
@Path("/UploadInvoice")
public class UploadController {
    /**
     * Framework-managed injection.
     * We delegate to the Service layer to keep this Controller
     * "thin" and focused only on handling the HTTP request/response.
     */
    @Inject
    InvoiceProcessingService invoiceProcessingService;

    /**
     * Receives a file, converts it to a stream, and triggers the AI pipeline.
     * Multipart/form-data allows us to stream the actual file bytes.
     * * @param file The uploaded file wrapped in a Quarkus FileUpload object.
     * @return A JSON response containing the AI-extracted invoice data.
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA) //tells the API that will receive upload file as multipart form data
    @Produces("application/json")
    public Response uploadInvoice(@RestForm("uploadInvoice") FileUpload file) {
        //FileUpload is a class in Quarkus RESt API to handle file upload in the rest API Info file name, size, path etc
        try {
// Converts the temporary file path into a stream for processing
            InputStream inputStream = Files.newInputStream(file.filePath());
            InvoiceEntity savedInvoice = invoiceProcessingService.processUploadedInvoice(
                    inputStream,
                    file.fileName(), file.contentType()
            );

            return Response.ok(Map.of(
                    "message", "File uploaded and processed successfully",
                    "status", "COMPLETED",
                    "data", savedInvoice // This will include the ID, URL, and AI data
            )).build();
        } catch (InvoiceValidationException e) {
            // This catches the  custom errors (Invalid format, AI math errors, etc.)
            return Response.status(400).entity(Map.of(
                    "errorType", e.getErrorCode(),
                    "details", e.getMessage()
            )).build();
        } catch (Exception e) {
            return Response.status(500).entity(Map.of(
                    "errorType", "UNKNOWN_ERROR",
                    "details", "Something went wrong on the server."
            )).build();
        }

    }
}

