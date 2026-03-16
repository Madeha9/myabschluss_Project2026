package at.madeha.intelliinvoice.service; // Make sure your package is correct

import at.madeha.intelliinvoice.business.InvoiceStatus;
import at.madeha.intelliinvoice.database.InvoiceEntity;
import at.madeha.intelliinvoice.service.helper.ReturnStatusInfo;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@ApplicationScoped // This tells Quarkus this is a Service, not a standalone program
public class InvoiceReturnService {

    public ReturnStatusInfo getReturnStatusUpdate(InvoiceEntity invoice) {
        if (invoice.getReturnDays() == null || invoice.getInvoiceDate() == null) {
            return new ReturnStatusInfo(InvoiceStatus.NON_RETURNABLE, 0);
        }

        long daysElapsed = ChronoUnit.DAYS.between(invoice.getInvoiceDate(), LocalDate.now());
        long daysLeft = invoice.getReturnDays() - daysElapsed;

        InvoiceStatus currentStatus;
        if (daysLeft > 0) {
            currentStatus = InvoiceStatus.RETURNABLE;
        } else {
            currentStatus = InvoiceStatus.NON_RETURNABLE;
            daysLeft = 0;
        }

        return new ReturnStatusInfo(currentStatus, daysLeft);
    }
}