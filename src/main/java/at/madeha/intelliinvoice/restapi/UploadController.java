package at.madeha.intelliinvoice.restapi;


/*
 * endpoint: download/upload the Invoice file talks to Stoarge store teh file  and to the LLM to send the file for processing
 * UploadController handles the upload request , upload the file Invoice image and pass it to the LLM/Stoarge
 * connection to the service layer and implement the endpoint
 * need to  use the storage service and processing service  , so that can store and process the invoice
 * inject the class means , instead of creating the instant  by myself, the framework do that for me, @inject invoiceproccservice
 * instead of writing something like PostprocessingService invc  = new  invoicerproceetime() each time ,
 * the framework creates once and could be used many times ,which makes the code cleaner
 *  that calls (dependency injection ) in quarkus
 */

import at.madeha.intelliinvoice.service.InvoiceProcessingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.InputStream;
import java.util.Map;


@Path("/UploadInvoice")
/*
 * the path is where the controller will respond for the request https.......Upload controller
 * pth is the URL for the API  endpoint
 */
public class UploadController {
   /*we use the interface instead of the class to avoid the full dependency on the class , if we change the
   CloudStorage class , we do not have to change the controller , because we do not  use
   the class , we are using the interface
    */
   //we call the invoiceProcessingService so that he saved the file to the cloud and return the url
    @Inject
   InvoiceProcessingService invoiceProcessingService; // The service that saves to DB
    @POST
   /*
   we use POSt Https request to create or send file to the server
    */
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    //tells the API that will recieve upload file that is multipart form data
    @Produces(MediaType.APPLICATION_JSON)
    //the API return a JSON file
   /*FileUpload is a class in Quarkus RESt API to handle file upload in the rest API
   contains information related to the file like file name, size, path etc
    */
    public Response uploadInvoice(
            @FormParam("uploadInvoice") InputStream fileInput,
            @FormParam("fileName") String fileName) {
        try {
            String fileUrl = invoiceProcessingService.handleUpload(fileInput, fileName);
            return Response.ok(Map.of(
                    "message", "File uploaded successfully",
                    "url", fileUrl
            )).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("File upload failed: " + e.getMessage())
                    .build();
        }
    }
}
//    public Response uploadInvoice(FileUpload uploadInvoice) {
//        try {
//           /*
//           the uploaded Invoice will be saved in the CloudStorage, we receive the file from the upload controller
//           then we pass it to the method upload file in the CouldStorage  in order to  save it  in the could
//           we pass the upload file from the controller to the cloud
//            */
//            String fileUrl = storageService.uploadFile(uploadInvoice);
//            return Response.ok(Map.of(
//                    "message", "File uploaded successfully",
//                    "url", fileUrl
//            )).build();
//        } catch (FileUploadException e) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity(Map.of("error", "UPLOAD_FAILED", "message", e.getMessage()))
//                    .build();
//        }
//    }
//}



