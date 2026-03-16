package at.madeha.intelliinvoice.service;

import at.madeha.intelliinvoice.business.Invoice;
import at.madeha.intelliinvoice.business.InvoiceItem;
import at.madeha.intelliinvoice.database.InvoiceEntity;
import at.madeha.intelliinvoice.database.InvoiceItemEntity;
import at.madeha.intelliinvoice.database.InvoiceRepository;
import at.madeha.intelliinvoice.infrastructure.InvoiceExtractor;
import at.madeha.intelliinvoice.service.helper.InvoiceUploadService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.io.InputStream;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

//    private InvoiceValidationService validationService; //to validate the info
//    @Inject
//    private InvoiceReturnService returnService;

   /*uploadHelper is a helper class  only to upload the image and return the url
      handelhelper calls the upload the class , and return the url
     */
/* Upload an invoice image then  create invoice entity in the database
 * we set the url of the image here
 * the url comes from the s3
 *      */

//    // List all invoices with return info, if there is return policy or not
//    public void listInvoicesWithReturnInfo() {
//        List<InvoiceEntity> invoices = repository.findAll();
// /*
// we use compareTo interface with BigDecima we can not use BigDecimal with > or < ,
// * CompareTo return 0,1,-1 , we compare it with 0 (this > 0),that means the difference is positive
// we set the value to 200 , using  BigDecimal.valueOf(200)) > 0, if the total amount is greater than 200 then
//  */
//        for (InvoiceEntity invoice : invoices) {
//            if (invoice.getTotalAmount().compareTo(BigDecimal.valueOf(200)) > 0) {
//                LOG.info("Invoice " + invoice.getId() + "You might get a bonus or discount with your store card!");
//            }
//            String returnMessage = returnService.checkReturnDays(invoice);
//            LOG.info("Invoice " + invoice.getId() + ": " + returnMessage);


@ApplicationScoped
public class InvoiceProcessingService {
    private static final Logger LOG = Logger.getLogger(InvoiceProcessingService.class);

    @Inject
    InvoiceUploadService uploadHelper; // Handles the S3/Cloud storage

    @Inject
    InvoiceExtractor extractor;
    // LangChain4j AI interface
    @Inject
    InvoiceValidationService validationService;

    @Inject
    InvoiceRepository repository;     // JPA Repository

    @Transactional
    public InvoiceEntity processUploadedInvoice(InputStream fileInput, String fileName, String contentType) {
        String imageUrl;

        //  UPLOAD ---
        try {
            imageUrl = uploadHelper.processUploadedInvoice(fileInput, fileName, contentType);
            LOG.info("Step 1 Success: Uploaded to " + imageUrl);
            //We need to convert the url to avoid the error before sending it to the model
            imageUrl = java.net.URI.create(imageUrl).toASCIIString();
        } catch (Exception e) {
            LOG.error("Step 1 FAILED (Upload): " + e.getMessage());
            throw new RuntimeException("Cloud Storage Error: Could not upload file.", e);
        }

        // --- STEP 2: AI EXTRACTION ---
        Invoice extractedData;
        try {
            extractedData = extractor.extract(imageUrl);
            LOG.info(" Success: AI extracted data for " + extractedData.getStoreName());
        } catch (Exception e) {
            // This is where your "Failed to parse" error is caught!
            LOG.error(" FAILED (AI): The AI returned text that isn't valid JSON.");
            LOG.error("Raw AI Error: " + e.getMessage());

            // Use a descriptive error so your Controller can tell the user
            throw new RuntimeException("AI Extraction Error: The AI could not read the invoice or returned an invalid format.", e);
        }

        // --- STEP 3: MAPPING & DB ---
        try {
            InvoiceEntity entity = new InvoiceEntity();
            entity.setImageUrl(imageUrl);
            entity.setStoreName(extractedData.getStoreName());
            entity.setTotalAmount(extractedData.getTotalAmount());
            entity.setInvoiceDate(extractedData.getInvoiceDate());
            entity.setCurrency(extractedData.getCurrency());
            Instant now = Instant.now();
            entity.setCreatedAt(now);
            entity.setUpdatedAt(now);

            if (extractedData.getItems() != null) {
                for (InvoiceItem itemDto : extractedData.getItems()) {
                    InvoiceItemEntity itemEntity = new InvoiceItemEntity();
                    itemEntity.setDescription(itemDto.getDescription());
                    itemEntity.setQuantity(itemDto.getQuantity());
                    itemEntity.setUnitPrice(itemDto.getUnitPrice());
                    itemEntity.setLineTotal(itemDto.getLineTotal());
                    itemEntity.setInvoice(entity);
                    entity.getItems().add(itemEntity);
                }
            }

            repository.save(entity);
            LOG.info(" Success: Saved ID " + entity.getId());
            return entity;

        } catch (Exception e) {
            LOG.error(" FAILED (Database): " + e.getMessage());
            throw new RuntimeException("Database Error: Could not save extracted data.", e);
        }
    }

    //TO Search the invoice using the store name
    public List<InvoiceEntity> searchByStoreName(String storeName) {
        LOG.info("Searching for invoices from: " + storeName);
        return repository.findAll().stream()
                .filter(inv -> inv.getStoreName() != null &&
                        inv.getStoreName().equalsIgnoreCase(storeName))
                .toList();
    }

    //  calculate total spending in a specific month
    public java.math.BigDecimal calculateMonthlySpending(int year, int month) {
        return repository.findAll().stream()
                .filter(inv -> inv.getInvoiceDate() != null &&
                        inv.getInvoiceDate().getYear() == year &&
                        inv.getInvoiceDate().getMonthValue() == month)
                .map(InvoiceEntity::getTotalAmount)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }

    // --- READ (Find by ID) ---
    public InvoiceEntity getInvoiceById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice with ID " + id + " not found."));
//                            ErrorCode.INVOICE_NOT_FOUND;

    }
    /*
    @findAll is a method to view all the saved invoices
     */

    public List<InvoiceEntity> findAll() {
        LOG.info("view all invoices");
        return repository.findAll();
    }

    // --- UPDATE (Manual Update) ---
    @Transactional
    public InvoiceEntity updateInvoice(InvoiceEntity invoice) {
//        validationService.validate(invoice);
        invoice.setUpdatedAt(Instant.now());
        return repository.save(invoice);
    }

    // --- DELETE Invoice by ID  ---
    /*
    we use the Transactional to ensure that if something went wrong nothing will be saved in the database
     */
    @Transactional
    public void deleteInvoiceById(UUID id) {
        LOG.info("Deleting invoice ID: " + id);
        try {
            repository.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Delete failed: Invoice not found.");
        }
    }
}