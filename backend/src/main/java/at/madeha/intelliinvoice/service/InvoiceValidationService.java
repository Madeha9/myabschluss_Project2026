package at.madeha.intelliinvoice.service;

import at.madeha.intelliinvoice.database.InvoiceEntity;
import at.madeha.intelliinvoice.database.InvoiceItemEntity;
import at.madeha.intelliinvoice.exception.ErrorCode;
import at.madeha.intelliinvoice.exception.InvoiceValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Validation service for technical and logical data integrity.
 * WHY: Stops invalid files early and audits AI math to ensure "Items + VAT = Total."
 */
@ApplicationScoped
public class InvoiceValidationService {
    private static final Logger LOG = Logger.getLogger(InvoiceValidationService.class);

    /**
     * Checks if the uploaded file is a valid image or PDF but not the content of the file.
     * Prevents system errors and saves AI costs by rejecting non-invoice files early.
     */
    public void validateFile(InputStream fileInput, String fileName, String contentType) {
        //  Basic Null Check, to be sure, the file has information that will be extracted
        if (fileInput == null || fileName == null || contentType == null) {
            throw new InvoiceValidationException(ErrorCode.INVALID_FILE_FORMAT, "File data is incomplete.");
        }
        // Verify MIME type and file extension match (JPG, PNG, or PDF)
        boolean isImage = contentType.startsWith("image/") &&
                (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg") || fileName.toLowerCase().endsWith(".png"));
        boolean isPdf = contentType.equals("application/pdf") && fileName.toLowerCase().endsWith(".pdf");

        if (!isImage && !isPdf) {
            throw new InvoiceValidationException(ErrorCode.INVALID_FILE_FORMAT, "Unsupported format. Please use PDF, PNG, or JPG.");
        }

        LOG.info("File Validation Passed: " + fileName);
    }

    /**
     * Orchestrates the validation of all extracted AI data.
     *  Ensures both general fields and specific line items meet business rules.
     */
    public void validate(InvoiceEntity invoice) {
        validateInvoiceFields(invoice);
        validateInvoiceItems(invoice);
    }

    /**
     * Validates required header fields like Store Name, Date, and Currency.
     */
    private void validateInvoiceFields(InvoiceEntity invoice) {
        if (invoice == null) {
            throw new InvoiceValidationException(ErrorCode.INVALID_INVOICE_DATA, "Invoice cannot be null");
        }
        if (invoice.getStoreName() == null || invoice.getStoreName().isBlank()) {
            throw new InvoiceValidationException(ErrorCode.INVALID_INVOICE_DATA, "Store name is required");
        }
        if (invoice.getInvoiceDate() == null) {
            throw new InvoiceValidationException(ErrorCode.INVALID_INVOICE_DATA, "Invoice date is required");
        }
        // Total must exist and be a positive number
        if (invoice.getTotalAmount() == null || invoice.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvoiceValidationException(ErrorCode.INVALID_INVOICE_DATA, "Total amount is required  and must be a positive number");
        }
        if (invoice.getCurrency() == null || invoice.getCurrency().length() != 3) {
            throw new InvoiceValidationException(ErrorCode.INVALID_INVOICE_DATA, "Currency must be 3 letters");
        }
    }

    /**
     * Performs a mathematical audit of the invoice items.
     * AI can make mistakes; we manually sum (Quantity * Price) + VAT to verify the total.
     */
    private void validateInvoiceItems(InvoiceEntity invoice) {
        List<InvoiceItemEntity> items = invoice.getItems();
        if (items == null || items.isEmpty()) {
            throw new InvoiceValidationException(ErrorCode.INVALID_INVOICE_DATA, "Invoice must have at least one item");
        }

        BigDecimal netSum = BigDecimal.ZERO;
        // Check for negative or zero values which are logically impossible for a sale
        //Calculate the sum of all items (The Net Price)
        for (InvoiceItemEntity item : items) {
            // Check for negative or zero values which are logically impossible for a sale , if the number of the Quantity like -1,-2 etc
            if (item.getQuantity() == null || item.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                throw new InvoiceValidationException(ErrorCode.INVALID_INVOICE_DATA, "Item quantity must be positive");
            }
            //check the item price , should not be negative
            if (item.getUnitPrice() == null || item.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new InvoiceValidationException(ErrorCode.INVALID_INVOICE_DATA, "Item unit price must be positive");
            }
            // Calculate line total and add to the running net sum
            BigDecimal lineTotal = item.getQuantity().multiply(item.getUnitPrice());
            netSum = netSum.add(lineTotal);
            item.setLineTotal(lineTotal);
        }
/*now if the AI extracted the Vat , then we use this value by multiply it with the sum , if the AI  did not find it
 we consider it ,like there is now taxes and Vat is zero BigDecimal.Zero , means set the vat to Zero, So
 Use extracted VAT or default to zero if not found
 */
        BigDecimal vat = (invoice.getVatAmount() != null) ? invoice.getVatAmount() : BigDecimal.ZERO;
        //this line to calculate the total amount of the money including the tax
        BigDecimal expectedTotal = netSum.add(vat).setScale(2, RoundingMode.HALF_UP);
        // Final Math, Compare our calculated total with the AI's reported total and throw  exception if mismatch
        if (expectedTotal.compareTo(invoice.getTotalAmount()) != 0) {
            throw new InvoiceValidationException(
                    ErrorCode.INVALID_INVOICE_DATA,
                    "Math error! Items (" + netSum + ") + VAT (" + vat + ") does not equal Total (" + invoice.getTotalAmount() + ")"
            );
        }
    }
}
