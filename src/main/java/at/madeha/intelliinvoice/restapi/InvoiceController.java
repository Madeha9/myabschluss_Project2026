package at.madeha.intelliinvoice.restapi;
/*
 * endpoint: talk to  the InvoiceProcessing service to delete , update  process the request for  the invoice
 * InvoiceController class to handle the CRUD, create, read,update and delete , in API @Get to list or view
 * ,  CRUD endpoint @Post to create ,@put to update  ,@Delete : endures use them directly
 * InvoiceController receive the request of Viewing the invoice the controller send the request to the service layer
 * to view or list the invoice , view and list are in the service layer , so we need to inject the service layer
 *  class to the controllers class , in order to be able and use their methods like find, delete etc
 * calls the InvoiceProcessingService Class
 */

import at.madeha.intelliinvoice.database.InvoiceEntity;
import at.madeha.intelliinvoice.service.InvoiceProcessingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

/*
 *  @Path(/the name of the path) defines the url door for  the controller, will open a website for the invoice, all th CRUD   for the
 * invoice controller will be under
 * this path
 */
@Path("/myinvoices")
/*
 * @Produces(MediaType.APPLICATION_JSON) all response will be JSON format
 *  @Consumes(MediaType.APPLICATION_JSON) receive request in JSON format
 * quarkus will translate the java object to the JSON and vice versa
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceController {
    //inject the InvoiceProcessingService so that we can use its method to create and process Invoices
    @Inject
    InvoiceProcessingService invoiceService;
    // instead of  InvoiceProcessingService invoiceService = new InvoiceProcessingService(), the qurakus does that

    @GET
    //@Get is to list or view/find the saved invoice
    public List<InvoiceEntity> getAllInvoices() {
        return invoiceService.getAllInvoices(); // call service
    }

    /*
     *  @post is to create a new invoice and save it in the database ,
     * so we call the method from the service pakage
     */
    @POST
    public InvoiceEntity createInvoice(InvoiceEntity invoice) {
        return invoiceService.createInvoice(invoice);
    }

    /*
    @Get and @path is to find by ID
     */
    @GET
    @Path("/{id}")
        /*
        to fetch the ID from the URL, the user is looking for an invoice with an ID, he will enter the ID, this url will
        * take the id from the url and compare it with the ID's in the Database
         */

    public InvoiceEntity getInvoice(@PathParam("id") UUID id) {
        return invoiceService.getInvoiceById(id);
    }
    //Update Method
//    @PUT
//    @Path("/{id}")
//    public Response updateInvoice(@PathParam("id") UUID id, InvoiceEntity invoice) {
//        InvoiceEntity updated = invoiceService.updateInvoice(id, invoice);
//        if (updated == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        return Response.ok(updated).build();
//    }

    @DELETE
    @Path("/{id}")
    public Response deleteInvoice(@PathParam("id") UUID id) {
        boolean deleted = invoiceService.deleteInvoice(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
/*
public Response getInvoice(@PathParam("id") UUID id) {
        try {
            // Call the service to fetch the invoice
            InvoiceEntity invoice = invoiceService.getInvoiceById(id);
            // Return 200 OK with invoice data
            return Response.ok(invoice).build();
        } catch (InvoiceValidationException e) {
            // Catch the custom exception and return a structured error response
            return Response.status(404) // HTTP status code
                           .entity(Map.of(
                               "error", e.getErrorCode(),  // enum value
                               "message", e.getMessage()   // descriptive message
                           ))
                           .build();
        }
    }
}
 */


/*
package at.madeha.intelliinvoice.restapi;

import at.madeha.intelliinvoice.service.InvoiceService;
import at.madeha.intelliinvoice.utilities.DateUtils;
import at.madeha.intelliinvoice.utilities.ValidationUtils;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Path("/invoices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceController {

    @Inject
    InvoiceService invoiceService;

    @POST
    public Response createInvoice(String storeName, BigDecimal totalAmount, String dateStr) {
        try {
            // ----- Validation using ValidationUtils -----
            if (ValidationUtils.isEmpty(storeName)) {
                return Response.status(400).entity("Store name cannot be empty").build();
            }

            if (!ValidationUtils.isPositive(totalAmount.doubleValue())) {
                return Response.status(400).entity("Total amount must be positive").build();
            }

            // ----- Parse date using DateUtils -----
            LocalDate invoiceDate = DateUtils.parseDate(dateStr);

            // ----- Call the service to create invoice -----
            invoiceService.createInvoice(storeName, totalAmount, invoiceDate);

            return Response.status(201).entity("Invoice created successfully").build();

        } catch (Exception e) {
            return Response.status(500).entity("Server error: " + e.getMessage()).build();
        }
    }
}
 */
