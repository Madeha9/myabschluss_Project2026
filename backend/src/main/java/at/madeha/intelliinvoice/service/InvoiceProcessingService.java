package at.madeha.intelliinvoice.service;

import at.madeha.intelliinvoice.business.Invoice;
import at.madeha.intelliinvoice.database.InvoiceEntity;
import at.madeha.intelliinvoice.database.InvoiceRepository;
import at.madeha.intelliinvoice.exception.ErrorCode;
import at.madeha.intelliinvoice.exception.InvoiceValidationException;
import at.madeha.intelliinvoice.infrastructure.InvoiceExtractor;
import at.madeha.intelliinvoice.service.helper.InvoiceUploadService;
import at.madeha.intelliinvoice.service.mapper.InvoiceMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * The central orchestrator for the invoice lifecycle.
 * This service manages the "Pipeline" from a raw file to a saved database entity.
 * It coordinates Cloud Storage, AI Extraction, and Validation in a specific order
 * to ensure no "junk" data enters the system.
 */
@ApplicationScoped
public class InvoiceProcessingService {
    private static final Logger LOG = Logger.getLogger(InvoiceProcessingService.class);
    @Inject
    InvoiceUploadService invoiceUploadService;
    @Inject
    InvoiceReturnService invoiceReturnService;
    @Inject
    InvoiceExtractor aiInvoiceParser;
    @Inject
    InvoiceValidationService invoiceValidationService;
    @Inject
    InvoiceRepository repository;
    @Inject
    InvoiceMapper invoiceMapper;

    /**
     * Executes the full multistep pipeline for an uploaded invoice.
     * We use @Transactional so that if any step (like AI or Database) fails,
     * the entire process rolls back to prevent partial or corrupted data.
     *
     * @param fileInput   The binary stream of the image/PDF.
     * @param fileName    Original name for metadata.
     * @param contentType MIME(Multipurpose Internet Mail Extensions (this PDF, image, etc.)) type to ensure format compatibility.
     * @return The final saved entity with AI data and S3 URL.
     * @throws InvoiceValidationException if any step in the pipeline fails.
     */
    @Transactional
    public InvoiceEntity processUploadedInvoice(InputStream fileInput, String fileName, String contentType) {
        String imageUrl;
        // We validate  first! If it's a "bad" file, stop and throw the exception.
        invoiceValidationService.validateFile(fileInput, fileName, contentType);
        //  then  UPLOAD  the file to the cloud after validate it
        try {
            imageUrl = invoiceUploadService.processUploadedInvoice(fileInput, fileName, contentType);
            LOG.info("Step 1 Success: Uploaded to " + imageUrl);
            //We need to convert the url to avoid the error before sending it to the model
            imageUrl = java.net.URI.create(imageUrl).toASCIIString();//we use it to deal with the spaces in the image name etc
        } catch (Exception e) {
            LOG.error("Step 1 FAILED (Upload): " + e.getMessage());
            throw new InvoiceValidationException(ErrorCode.FILE_UPLOAD_FAILED, "Cloud Storage Error: Could not upload file.", e);
        }
        // STEP 2: AI EXTRACTION  to extract the info from the Invoice
        Invoice extractedData;
        try {
            extractedData = aiInvoiceParser.extract(imageUrl);
            LOG.info(" Success: AI extracted data for " + extractedData.getStoreName());
        } catch (Exception e) {
            LOG.error(" FAILED (AI): The AI returned text that isn't valid JSON.");
            LOG.error("Raw AI Error: " + e.getMessage());
            throw new InvoiceValidationException(ErrorCode.INVALID_INVOICE_DATA, "AI Extraction Error: The AI could not read the invoice or returned an invalid format.", e);
        }
        //  MAPPING  the object to the DB
        InvoiceEntity entity = invoiceMapper.mapToEntity(extractedData, imageUrl);
        //  VALIDATE AI DATA
        invoiceValidationService.validate(entity);
        //  SAVE TO DATABASE
        try {

            repository.save(entity);
            LOG.info(" Success: Saved ID " + entity.getId());
            return entity;
        } catch (Exception e) {
            LOG.error(" FAILED (Database): " + e.getMessage());
        }
        throw new InvoiceValidationException(ErrorCode.DATABASE_ERROR, "Failed to save to database.");
    }

    /**
     * Searches for invoices in the database by a specific store name.
     * Provides the frontend with a way to filter large receipt lists
     * without reloading the entire page.
     */
    public List<InvoiceEntity> searchByStoreName(String storeName) {
        LOG.info("Searching for invoices containing: " + storeName);

        //  to ignore the lower cases issues
        String searchLower = storeName.toLowerCase();

        return repository.findAll().stream()
                .filter(inv -> inv.getStoreName() != null &&
                        inv.getStoreName().toLowerCase().contains(searchLower))
                .toList();
    }

    /**
     * Aggregates the total spending for a specific month and year.
     * Powers the "Spending Dashboard" by using Java Streams to
     * filter and sum BigDecimal values safely.
     */
    public java.math.BigDecimal calculateMonthlySpending(int year, int month) {
        try {
            return repository.findAll().stream()
                    .filter(inv -> inv.getInvoiceDate() != null &&
                            inv.getInvoiceDate().getYear() == year &&
                            inv.getInvoiceDate().getMonthValue() == month)
                    .map(InvoiceEntity::getTotalAmount)
                    .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
        } catch (Exception e) {
            LOG.error("Error calculating monthly total: " + e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    /**
     * Fetches a single invoice or throws an exception if the ID is invalid.
     */
    public InvoiceEntity getInvoiceById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new InvoiceValidationException(ErrorCode.INVOICE_NOT_FOUND, "Invoice with ID " + id + " not found."));
    }

    /** Returns a list of all stored invoices for the main overview. */
    public List<InvoiceEntity> findAll() {
        LOG.info("view all invoices");
        return repository.findAll();
    }

    /**
     * Removes an invoice record permanently.*/
    @Transactional
    public void deleteInvoiceById(UUID id) {
        LOG.info("Deleting invoice ID: " + id);
        try {
            repository.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw new InvoiceValidationException(ErrorCode.INVOICE_NOT_FOUND, "Delete failed: Invoice not found.");
        }
    }
}