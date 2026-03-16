package at.madeha.intelliinvoice.service;

import at.madeha.intelliinvoice.database.InvoiceEntity;
import at.madeha.intelliinvoice.database.InvoiceItemEntity;
import at.madeha.intelliinvoice.exception.InvoiceValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class InvoiceValidationService {
    private static final Logger LOG = Logger.getLogger(InvoiceValidationService.class);

    // --- NEW METHOD: Validate the Physical File ---
    public void validateFile(InputStream fileInput, String fileName, String contentType) {
        if (fileInput == null || fileName == null || fileName.isEmpty()) {
            throw new InvoiceValidationException("File or filename is missing.");
        }

        // Check File Extension
        String lowerName = fileName.toLowerCase();
        if (!(lowerName.endsWith(".pdf") || lowerName.endsWith(".png") ||
                lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg"))) {
            throw new InvoiceValidationException("Unsupported file type: " + fileName + ". Please upload PDF, PNG, or JPG.");
        }

        // Check Content Type (MIME)
        if (contentType == null || (!contentType.startsWith("image/") && !contentType.equals("application/pdf"))) {
            throw new InvoiceValidationException("Invalid content type: " + contentType);
        }

        LOG.info("File Validation Passed: " + fileName);
    }

    // --- EXISTING METHOD: Validate the AI Data ---
    public void validate(InvoiceEntity invoice) {
        validateInvoiceFields(invoice);
        validateInvoiceItems(invoice);
    }

    private void validateInvoiceFields(InvoiceEntity invoice) {
        if (invoice == null) {
            throw new InvoiceValidationException("Invoice cannot be null");
        }
        if (invoice.getStoreName() == null || invoice.getStoreName().isBlank()) {
            throw new InvoiceValidationException("Store name is required");
        }
        if (invoice.getInvoiceDate() == null) {
            throw new InvoiceValidationException("Invoice date is required");
        }
        if (invoice.getTotalAmount() == null || invoice.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvoiceValidationException("Total amount must be greater than zero");
        }
        if (invoice.getCurrency() == null || invoice.getCurrency().length() != 3) {
            throw new InvoiceValidationException("Currency must be 3 letters");
        }
    }

    private void validateInvoiceItems(InvoiceEntity invoice) {
        List<InvoiceItemEntity> items = invoice.getItems();
        if (items == null || items.isEmpty()) {
            throw new InvoiceValidationException("Invoice must have at least one item");
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (InvoiceItemEntity item : items) {
            if (item.getQuantity() == null || item.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                throw new InvoiceValidationException("Item quantity must be positive");
            }
            if (item.getUnitPrice() == null || item.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new InvoiceValidationException("Item unit price must be positive");
            }
            BigDecimal lineTotal = item.getQuantity().multiply(item.getUnitPrice());
            sum = sum.add(lineTotal);
            item.setLineTotal(lineTotal);
        }

        if (sum.compareTo(invoice.getTotalAmount()) != 0) {
            throw new InvoiceValidationException("Invoice total does not match sum of items. Sum: " + sum + ", Total: " + invoice.getTotalAmount());
        }
    }
}
