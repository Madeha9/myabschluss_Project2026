package at.madeha.intelliinvoice.service;

import at.madeha.intelliinvoice.business.Invoice;
import at.madeha.intelliinvoice.business.InvoiceItem;
import at.madeha.intelliinvoice.business.InvoiceStatus;
import at.madeha.intelliinvoice.database.InvoiceEntity;
import at.madeha.intelliinvoice.database.InvoiceItemEntity;
import at.madeha.intelliinvoice.database.InvoiceRepository;
import at.madeha.intelliinvoice.exception.ErrorCode;
import at.madeha.intelliinvoice.exception.InvoiceValidationException;
import at.madeha.intelliinvoice.infrastructure.InvoiceExtractor;
import at.madeha.intelliinvoice.service.helper.InvoiceUploadService;
import at.madeha.intelliinvoice.service.helper.ReturnStatusInfo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.jboss.logging.Logger;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
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
// /*
// we use compareTo interface with BigDecima we can not use BigDecimal with > or < ,
// * CompareTo return 0,1,-1 , we compare it with 0 (this > 0),that means the difference is positive
// we set the value to 200 , using  BigDecimal.valueOf(200)) > 0, if the total amount is greater than 200 then
//  */



@ApplicationScoped
public class InvoiceProcessingService {
    private static final Logger LOG = Logger.getLogger(InvoiceProcessingService.class);

    @Inject
    InvoiceUploadService uploadHelper; // Handles the S3/Cloud storage
    @Inject
    InvoiceReturnService invoiceReturnService;
    @Inject
    InvoiceExtractor extractor;
    // LangChain4j AI interface
    @Inject
    InvoiceValidationService invoiceValidationService;

    @Inject
    InvoiceRepository repository;     // JPA Repository

    @Transactional
    public InvoiceEntity processUploadedInvoice(InputStream fileInput, String fileName, String contentType) {
        String imageUrl;
        //first the  VALIDATE FILE
        // We do this first! If it's a "bad" file, we stop here and throw the exception.
        invoiceValidationService.validateFile(fileInput, fileName, contentType);
        //  then  UPLOAD  the file to the cloud after validate it
        try {
            imageUrl = uploadHelper.processUploadedInvoice(fileInput, fileName, contentType);
            LOG.info("Step 1 Success: Uploaded to " + imageUrl);
            //We need to convert the url to avoid the error before sending it to the model
            imageUrl = java.net.URI.create(imageUrl).toASCIIString();//we use it to deal with the spaces in the image name etc..
        } catch (Exception e) {
            LOG.error("Step 1 FAILED (Upload): " + e.getMessage());
            throw new InvoiceValidationException(ErrorCode.FILE_UPLOAD_FAILED, "Cloud Storage Error: Could not upload file.", e);
        }

        // STEP 2: AI EXTRACTION  to extract the info from the Invoice
        Invoice extractedData;
        try {
            extractedData = extractor.extract(imageUrl);
            LOG.info(" Success: AI extracted data for " + extractedData.getStoreName());
        } catch (Exception e) {
            // This is where your "Failed to parse" error is caught!
            LOG.error(" FAILED (AI): The AI returned text that isn't valid JSON.");
            LOG.error("Raw AI Error: " + e.getMessage());

            // Use a descriptive error so your Controller can tell the user
            throw new InvoiceValidationException(ErrorCode.INVALID_INVOICE_DATA, "AI Extraction Error: The AI could not read the invoice or returned an invalid format.", e);
        }

        //  MAPPING  the object to the DB ---
        InvoiceEntity entity = mapToEntity(extractedData, imageUrl);
        // --- STEP 4: VALIDATE AI DATA ---
        // Now we check if the AI found a store name, a positive total, etc.
        invoiceValidationService.validate(entity);
        // --- STEP 5: SAVE TO DATABASE ---
        try {
            repository.save(entity);
            LOG.info(" Success: Saved ID " + entity.getId());
            return entity;
        } catch (Exception e) {
            LOG.error(" FAILED (Database): " + e.getMessage());
        }
        throw new InvoiceValidationException(ErrorCode.DATABASE_ERROR, "Failed to save to database.");
    }

    // a helper Method
    private InvoiceEntity mapToEntity(Invoice extractedData, String imageUrl) {
            InvoiceEntity entity = new InvoiceEntity();
            /*the Invoice enum ....
            to set the Invoice Status  and the day left to retun the Invoice
             */
            entity.setImageUrl(imageUrl);
            entity.setStoreName(extractedData.getStoreName());
            entity.setTotalAmount(extractedData.getTotalAmount());
            entity.setInvoiceDate(extractedData.getInvoiceDate());
            entity.setCurrency(extractedData.getCurrency());
        entity.setVatAmount(extractedData.getVatAmount());
        entity.setInvoiceNumber(extractedData.getInvoiceNumber());
            ReturnStatusInfo returnStatusInfo = invoiceReturnService.getReturnStatusUpdate(entity);
            InvoiceStatus status = returnStatusInfo.status(); //se the Status from the enum and the record after getting the data from the AI so that we
            //we can use the Invoice date and it is nt null anymore
            LOG.info("Invoice processed. Days left: " + returnStatusInfo.daysLeft());// the left days is not savd in the database
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
            return entity;
    }

    //TO Search the invoice using the store name
    public List<InvoiceEntity> searchByStoreName(String storeName) {
        LOG.info("Searching for invoices from: " + storeName);
        return repository.findAll().stream()
                .filter(inv -> inv.getStoreName() != null &&
                        inv.getStoreName().equalsIgnoreCase(storeName))
                .toList();
    }

    /* calculate total spending in a specific month
    using the stream and the lambda and the Method references
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
    // --- READ (Find by ID) ---
    public InvoiceEntity getInvoiceById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new InvoiceValidationException(ErrorCode.INVOICE_NOT_FOUND, "Invoice with ID " + id + " not found."));
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
    public Optional<InvoiceEntity> updateInvoice(InvoiceEntity invoice) {
        Optional<InvoiceEntity> existing = repository.findById(invoice.getId());
        if (existing == null) {
            throw new NotFoundException("Invoice not found with ID: " + invoice.getId());
        }
//        //  Map only the fields you want to allow the user to change
//        existing.setStoreName(invoice.getStoreName());
//        existing.setTotalAmount(invoice.getTotalAmount());
//        existing.setVatAmount(invoice.getVatAmount());
//        existing.setCurrency(invoice.getCurrency());
//        existing.setInvoiceDate(invoice.getInvoiceDate());
//        // ... add any other fields you want to update
//
//        // 3. Update the "Modified" timestamp
//        // 4. In Quarkus/Hibernate, you don't even need to call 'save'!
//        // Because of @Transactional, any changes to 'existing' are saved automatically.
        return existing;
//        existing.setUpdatedAt(Instant.now());
    }

    // DELETE Invoice by ID
    /*
    we use the Transactional to ensure that if something went wrong nothing will be saved in the database
     */
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