package at.madeha.intelliinvoice.service;

import at.madeha.intelliinvoice.database.InvoiceEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.logging.Logger;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@ApplicationScoped
public class InvoiceReturnService {
    private static final Logger LOG = Logger.getLogger(InvoiceReturnService.class);

    @Inject
    private EntityManager em;

    // Check return days and return message
    public String checkReturnDays(InvoiceEntity invoice) {
        Integer returnDays = invoice.getReturnDays();
        if (returnDays == null) {
            return "There is no return policy for this invoice.";
        }

        long daysElapsed = ChronoUnit.DAYS.between(invoice.getInvoiceDate(), LocalDate.now());
        long daysLeft = returnDays - daysElapsed;

        if (daysLeft > 0) {
            return "You can return this invoice within " + daysLeft + " day(s).";
        } else {
            return "This invoice is no longer returnable.";
        }
    }

    // Calculate total for a given month
    public BigDecimal calculateMonthlyTotal(int year, int month) {
        try {
            List<InvoiceEntity> invoices = em.createQuery(
                            "SELECT i FROM InvoiceEntity i WHERE YEAR(i.invoiceDate) = :year AND MONTH(i.invoiceDate) = :month",
                            InvoiceEntity.class)
                    .setParameter("year", year)
                    .setParameter("month", month)
                    .getResultList();

            BigDecimal total = BigDecimal.ZERO;
            for (InvoiceEntity invoice : invoices) {
                total = total.add(invoice.getTotalAmount());
            }
            return total;

        } catch (Exception e) {
            LOG.error("Error calculating monthly total: " + e.getMessage());
            return BigDecimal.ZERO;
        }
    }
}