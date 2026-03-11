package at.madeha.intelliinvoice.service;

import at.madeha.intelliinvoice.database.InvoiceEntity;
import at.madeha.intelliinvoice.database.InvoiceItemEntity;
import at.madeha.intelliinvoice.database.InvoiceRepository;
import at.madeha.intelliinvoice.infrastructure.InvoiceExtractor;
import at.madeha.intelliinvoice.service.helper.InvoiceUploadService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class InvoiceProcessingService {
    /*
    using logger insteadof System out printin
     */
    private static final Logger LOG = Logger.getLogger(InvoiceProcessingService.class);
    /*
    the class do the bussein workflow and mean business logic
    the llm will be called here in order to extract the info
     */
    @Inject
    InvoiceExtractor invoiceExtractor;
    @Inject
    InvoiceUploadService uploadHelper; //this return the url image after saving it in the s3
    @Inject
    private InvoiceValidationService validationService;
    @Inject
    private InvoiceReturnService returnService;
    @Inject
    private InvoiceRepository repository;

    /*uploadHelper is a helper class  only to upload the image and return the url
    handelhelper calls the upload the class , and return the url
     */
    public String handleUpload(InputStream fileInput, String fileName) {

        String imageUrl = uploadHelper.processUploadedInvoice(fileInput, fileName);
        LOG.info("Image uploaded: " + imageUrl);
        return imageUrl;
    }

    /* Upload an invoice image then  create invoice entity in the database
     * we set the url of the image here
     * the url comes fromthe s3
     */
    public InvoiceEntity createInvoice(InvoiceEntity invoice) {
        // upload file and get the URL
        validationService.validate(invoice);
        //now is from the type Instant , to set up the data automatic
        Instant now = Instant.now();
        invoice.setCreatedAt(now);
        invoice.setUpdatedAt(now);
//        invoice.setImageUrl(cloudStorageService.uploadFile());
        //linking the child items to the parent invoice, so that each invoice will have an invoice items
        for (InvoiceItemEntity item : invoice.getItems()) {
            item.setInvoice(invoice);
        }
        //to calculate the total monthly  amount
        InvoiceEntity savedInvoice = repository.save(invoice);
        LOG.info("Invoice saved successfully!");
        BigDecimal monthlyTotal = returnService.calculateMonthlyTotal(
                invoice.getInvoiceDate().getYear(),
                invoice.getInvoiceDate().getMonthValue());
        LOG.info("Monthly total for " + invoice.getInvoiceDate().getMonth() + ": " + monthlyTotal);
        LOG.info("Invoice saved successfully with URL: " + invoice.getImageUrl());
        return savedInvoice;
    }

    // List all invoices with return info, if there is return policy or not
    public void listInvoicesWithReturnInfo() {
        List<InvoiceEntity> invoices = repository.findAll();
 /*
 we use compareTo interface with BigDecima we can not use BigDecimal with > or < ,
 * CompareTo return 0,1,-1 , we compare it with 0 (this > 0),that means the difference is positive
 we set the value to 200 , using  BigDecimal.valueOf(200)) > 0, if the total amount is greater than 200 then
  */
        for (InvoiceEntity invoice : invoices) {
            if (invoice.getTotalAmount().compareTo(BigDecimal.valueOf(200)) > 0) {
                LOG.info("Invoice " + invoice.getId() + "You might get a bonus or discount with your store card!");
            }
            String returnMessage = returnService.checkReturnDays(invoice);
            LOG.info("Invoice " + invoice.getId() + ": " + returnMessage);
        }
    }

    //Find all invoices
    public List<InvoiceEntity> getAllInvoices() {
        return repository.findAll();
    }

    //find by id
    /*
    public InvoiceEntity getInvoiceById(UUID invoiceId) {
    return repository.findById(invoiceId)
            .orElseThrow(() -> new InvoiceValidationException(
                    ErrorCode.INVOICE_NOT_FOUND,
                    "Invoice with id " + invoiceId + " not found"
            ));
}
     */
    public InvoiceEntity getInvoiceById(UUID id) {
        return repository.findById(id).orElse(null); // returns null if not found
    }

    // Delete invoice
    /*
    Optional<InvoiceEntity>
    Optional is a container that may or may not contain a value (to avoid null issues)
    orElseThrow a Method in optional , using lambda
     */
    public boolean deleteInvoice(UUID invoiceId) {
        InvoiceEntity invoice = repository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        repository.deleteById(invoiceId);
        LOG.info("Invoice deleted! Amount: " + invoice.getTotalAmount());
        return false;
    }
}
/* check later on
public BigDecimal calculateMonthlyTotal(int year, int month) {
    return repository.findAll() // returns List<InvoiceEntity>
            .stream()
            .filter(inv -> inv.getInvoiceDate().getYear() == year
                        && inv.getInvoiceDate().getMonthValue() == month)
            .map(InvoiceEntity::getTotalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add); // sum all totals
}
 */
/*
public void markInvoiceAsPaid(UUID invoiceId) {
    InvoiceEntity invoice = repository.findById(invoiceId).orElseThrow();
    invoice.setStatus(InvoiceStatus.PAID); // set enum
    repository.persist(invoice);
}
 */