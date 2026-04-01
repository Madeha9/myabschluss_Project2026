package at.madeha.intelliinvoice.service; // Make sure your package is correct

import at.madeha.intelliinvoice.business.InvoiceStatus;
import at.madeha.intelliinvoice.database.InvoiceEntity;
import at.madeha.intelliinvoice.service.helper.ReturnStatusInfo;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Specialist Service for calculating Return Deadlines.
 * Invoices are time-sensitive. This service provides the core "Return Window"
 * logic that gives the app its unique value, helping users avoid missing refund deadlines.
 */
@ApplicationScoped // This tells Quarkus this is a Service, not a standalone program
public class InvoiceReturnService {
    /**
     * Calculates if an invoice is still returnable and how many days remain.
     * * @param invoice The entity containing the purchase date and the store's return policy.
     *
     * @return A ReturnStatusInfo record containing the Enum status and the numeric countdown.
     */
    public ReturnStatusInfo getReturnStatusUpdate(InvoiceEntity invoice) {
        // Null Check: If we don't have a date or a policy, we assume it cannot be returned.
        if (invoice.getReturnDays() == null || invoice.getInvoiceDate() == null) {
            return new ReturnStatusInfo(InvoiceStatus.NON_RETURNABLE, 0);
        }
// Calculate the difference between the purchase date and today
        long daysElapsed = ChronoUnit.DAYS.between(invoice.getInvoiceDate(), LocalDate.now());
        long daysLeft = invoice.getReturnDays() - daysElapsed;

        InvoiceStatus currentStatus;
        if (daysLeft > 0) {
            currentStatus = InvoiceStatus.RETURNABLE;
        } else {
            // Even if the math is negative (e.g., -5 days), we show 0 days left for the user.
            currentStatus = InvoiceStatus.NON_RETURNABLE;
            daysLeft = 0;
        }

        return new ReturnStatusInfo(currentStatus, daysLeft);
    }
}