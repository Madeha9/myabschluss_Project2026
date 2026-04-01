package at.madeha.intelliinvoice.restapi;

import at.madeha.intelliinvoice.database.InvoiceEntity;
import at.madeha.intelliinvoice.exception.InvoiceValidationException;
import at.madeha.intelliinvoice.service.InvoiceProcessingService;
import at.madeha.intelliinvoice.service.InvoiceReturnService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Main entry point for the Frontend to manage lifecycle of an invoice.
 * We use an API Controller here to decouple our User Interface (Angular)
 * from our Business Logic (Service Layer).
 */
@Path("/myinvoices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceController {
    @Inject
    InvoiceProcessingService invoiceService;
    @Inject
    InvoiceReturnService invoiceReturnService;

    /**
     * Combines stored invoice data with real-time "Return Status" logic
     * to show the user if an invoice is still within its legal return window.
     */
    @GET
    public List<InvoiceResponseDTO> getAllInvoices() {
        return invoiceService.findAll().stream()
                .map(entity -> {
                    var info = invoiceReturnService.getReturnStatusUpdate(entity);
                    // Wrap it in the DTO
                    return new InvoiceResponseDTO(entity, info);
                })
                .toList();
    }

    /**
     * Fetches a single invoice using UUID to ensure globally unique identification.
     */
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

    /**
     * Filtered search to help users find specific receipts without loading the entire list.
     */
    @GET
    @Path("/search")
    public List<InvoiceEntity> searchByStore(@QueryParam("name") String name) {
        return invoiceService.searchByStoreName(name);
    }

    /**
     * Removes the invoice record; error handling is specialized to catch missing IDs.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteInvoice(@PathParam("id") UUID id) {
        try {
            // Calls the deleteInvoiceById we added to the service
            invoiceService.deleteInvoiceById(id);
            return Response.ok(Map.of(
                    "message", "Invoice " + id + " has been deleted successfully",
                    "status", "DELETED"
            )).build();
        } catch (InvoiceValidationException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("code", e.getErrorCode(), "message", e.getMessage()))
                    .build();
        } catch (Exception e) {
            // General net for things like database crashes
            return Response.status(500).entity("An unexpected error occurred: " + e.getMessage()).build();
        }
    }

    /**
     * Aggregates financial data for the Spending Dashboard.
     * Requires both year and month to prevent expensive full-database scans.
     */
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

