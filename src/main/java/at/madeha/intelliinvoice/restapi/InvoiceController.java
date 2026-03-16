/// *
// * endpoint: talk to  the InvoiceProcessing service to delete , update  process the request for  the invoice
// * InvoiceController class to handle the CRUD, create, read,update and delete , in API @Get to list or view
// * ,  CRUD endpoint @Post to create ,@put to update  ,@Delete : endures use them directly
// * InvoiceController receive the request of Viewing the invoice the controller send the request to the service layer
// * to view or list the invoice , view and list are in the service layer , so we need to inject the service layer
// *  class to the controllers class , in order to be able and use their methods like find, delete etc
// * calls the InvoiceProcessingService Class
// */
//
// *  @Path(/the name of the path) defines the url door for  the controller, will open a website for the invoice, all th CRUD   for the
// * invoice controller will be under
// * this path
// */
//@Path("/myinvoices")
///*
// * @Produces(MediaType.APPLICATION_JSON) all response will be JSON format
// *  @Consumes(MediaType.APPLICATION_JSON) receive request in JSON format
// * quarkus will translate the java object to the JSON and vice versa
// */
//    //inject the InvoiceProcessingService so that we can use its method to create and process Invoices
//    InvoiceProcessingService invoiceService;
//    // instead of  InvoiceProcessingService invoiceService = new InvoiceProcessingService(), the qurakus does that
//    //@Get is to list or view/find the saved invoice
/*
 *  @post is to create a new invoice and save it in the database ,
 * so we call the method from the service pakage , but it is laready in -the uplaod
 */
   /*
//    @Get and @path is to find by ID
//     */
//    @Path("/{id}")
//        /*
//        to fetch the ID from the URL, the user is looking for an invoice with an ID, he will enter the ID, this url will
//        * take the id from the url and compare it with the ID's in the Database
//         */

package at.madeha.intelliinvoice.restapi;

import at.madeha.intelliinvoice.database.InvoiceEntity;
import at.madeha.intelliinvoice.service.InvoiceProcessingService;
import at.madeha.intelliinvoice.service.InvoiceReturnService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/myinvoices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceController {

    @Inject
    InvoiceProcessingService invoiceService;
    @Inject
    InvoiceReturnService invoiceReturnService;

    // find all the Invoice
    @GET
    public List<InvoiceResponseDTO> getAllInvoices() {
        return invoiceService.findAll().stream()
                .map(entity -> {
                    //  calculation of the lefted days and the status
                    var info = invoiceReturnService.getReturnStatusUpdate(entity);
                    // Wrap it in the DTO
                    return new InvoiceResponseDTO(entity, info);
                })
                .toList();
    }

    // view by id
    @GET
    @Path("/{id}")
    public Response getInvoice(@PathParam("id") UUID id) {
        try {
            InvoiceEntity entity = invoiceService.getInvoiceById(id);
            //var so that the controller can guess , the type of the variable during the compiling, here replaced the retunstatusInfo
            var info = invoiceReturnService.getReturnStatusUpdate(entity);

            return Response.ok(new InvoiceResponseDTO(entity, info)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    // --- SEARCH BY STORE NAME ---
    @GET
    @Path("/search")
    public List<InvoiceEntity> searchByStore(@QueryParam("name") String name) {
        return invoiceService.searchByStoreName(name);
    }

    // --- UPDATE ---
    @PUT
    public Response updateInvoice(InvoiceEntity invoice) {
        try {
            // This calls the updateInvoice method we just added to the service
            InvoiceEntity updated = invoiceService.updateInvoice(invoice);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    // --- DELETE ---
    @DELETE
    @Path("/{id}")
    public Response deleteInvoice(@PathParam("id") UUID id) {
        try {
            // Calls the deleteInvoiceById we added to the service
            invoiceService.deleteInvoiceById(id);
            return Response.noContent().build(); // 204 No Content is standard for successful delete
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    // --- ANALYTICS / SPENDING ---
    @GET
    @Path("/spending")
    public Response getMonthlySpending(@QueryParam("year") int year, @QueryParam("month") int month) {
        // Validation to ensure year and month are provided
        if (year == 0 || month == 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Year and Month are required parameters.").build();
        }

        java.math.BigDecimal total = invoiceService.calculateMonthlySpending(year, month);

        // Return as a simple JSON object
        return Response.ok(Map.of(
                "year", year,
                "month", month,
                "totalSpending", total
        )).build();
    }
}

