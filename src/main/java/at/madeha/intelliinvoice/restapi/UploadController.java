package at.madeha.intelliinvoice.restapi;

//
/// *
// * endpoint: download/upload the Invoice file talks to Stoarge store teh file  and to the LLM to send the file for processing
// * UploadController handles the upload request , upload the file Invoice image and pass it to the LLM/Stoarge
// * connection to the service layer and implement the endpoint
// * need to  use the storage service and processing service  , so that can store and process the invoice
// * inject the class means , instead of creating the instant  by myself, the framework do that for me, @inject invoiceproccservice
// * instead of writing something like PostprocessingService invc  = new  invoicerproceetime() each time ,
// * the framework creates once and could be used many times ,which makes the code cleaner
// *  that calls (dependency injection ) in quarkus
// */
//
//import at.madeha.intelliinvoice.database.InvoiceEntity;
//import at.madeha.intelliinvoice.service.InvoiceProcessingService;
//import jakarta.inject.Inject;
//import jakarta.ws.rs.*;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//
//import java.io.InputStream;
//import java.util.Map;
//
//
//@Path("/UploadInvoice")
///*
// * the path is where the controller will respond for the request https.......Upload controller
// * pth is the URL for the API  endpoint
// */
//public class UploadController {
//   /*we use the interface instead of the class to avoid the full dependency on the class , if we change the
//   CloudStorage class , we do not have to change the controller , because we do not  use
//   the class , we are using the interface
//    */
//   //we call the invoiceProcessingService so that he saved the file to the cloud and return the url
//    @Inject
//   InvoiceProcessingService invoiceProcessingService; // The service that saves to DB
//    @POST
//   /*
//   we use POSt Https request to create or send file to the server
//    */
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    //tells the API that will recieve upload file that is multipart form data
//    @Produces(MediaType.APPLICATION_JSON)
//    //the API return a JSON file
//   /*FileUpload is a class in Quarkus RESt API to handle file upload in the rest API
//   contains information related to the file like file name, size, path etc
//    */
//    public Response uploadInvoice(
//            @FormParam("uploadInvoice") InputStream fileInput,
//            @FormParam("fileName") String fileName) {
//        try {
//            InvoiceEntity fileUrl = invoiceProcessingService.processUploadedInvoice(fileInput, fileName);
//            return Response.ok(Map.of(
//                    "message", "File uploaded successfully",
//                    "url", fileUrl
//            )).build();
//
//        } catch (Exception e) {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//                    .entity("File upload failed: " + e.getMessage())
//                    .build();
//        }
//    }
//}

import at.madeha.intelliinvoice.database.InvoiceEntity;
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

@Path("/UploadInvoice")
public class UploadController {

    @Inject
    InvoiceProcessingService invoiceProcessingService; // Framework handles this!

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json") // This is the same as MediaType.APPLICATION_JSON
    public Response uploadInvoice(@RestForm("uploadInvoice") FileUpload file) {
        try {
            // file.fileName() gets the real name (e.g., "receipt.jpg")
            // file.contentType() gets the type (e.g., "image/jpeg")
            // file.filePath() gives the path to the temp file

            InputStream is = Files.newInputStream(file.filePath());

            InvoiceEntity savedInvoice = invoiceProcessingService.processUploadedInvoice(
                    is,
                    file.fileName(), file.contentType()
            );

            return Response.ok(Map.of(
                    "message", "File uploaded and processed successfully",
                    "status", "COMPLETED",
                    "data", savedInvoice // This will include the ID, URL, and AI data
            )).build();
        } catch (Exception e) {
            return Response.status(500).entity(Map.of(
                    "message", "File upload failed",
                    "error", e.getMessage()
            )).build();
        }
    }
}

