package at.madeha.intelliinvoice.business;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Represents a plain Java object (POJO) used as a Data Transfer Object (DTO)
 * * This class is used to map extracted data from the LLM into a structured format
 * before it is converted into a database entity. It decoupled the AI extraction
 * logic from the persistence layer.
 * This class serves as a temporary data holder before persistence.
 */
public class Invoice {
    /**
     * Unique identifier for the invoice instance automatically generated in the database .
     */
    private UUID id;
    /** The date the invoice was issued  AI returns it  */
    private LocalDate invoiceDate;
    private String storeName;
    private BigDecimal totalAmount;
    private String currency;
    /** The link to the stored image of the original invoice in the cloud. */
    private String imageUrl;
    /** Maximum days allowed for a return per store policy. */
    private Integer returnDays;
    private InvoiceStatus status;
    private BigDecimal vatAmount;
    private Integer invoiceNumber;
    private List<InvoiceItem> items;


    /**
     * Default constructor required for JSON mapping and frameworks.
     */
    public Invoice() {
    }

    /**
     * Full constructor for manual initialization of all invoice fields.
     *      * @param id             the unique identifier for this invoice instance
     *      * @param invoiceDate    the date printed on the physical receipt
     *      * @param storeName      the name of the  shop
     *      * @param totalAmount    the final price including all taxes
     *      * @param currency       the currency code used (e.g., EUR, USD)
     *      * @param status         the initial return eligibility status
     *      * @param vatAmount      the value-added tax amount identified by the AI
     *      * @param invoiceNumber  the reference or receipt number from the paper
     *      * @param imageUrl        The link to the stored image of the original invoice in the cloud.
     *      * @param returnDays     the total days allowed for a return
     *      * @param items          the list of individual products found on the receipt
     *      */

    public Invoice(UUID id, LocalDate invoiceDate, String storeName,
                   BigDecimal totalAmount, String currency, InvoiceStatus status, BigDecimal vatAmount, Integer invoiceNumber,
                   String imageUrl, Integer returnDays, List<InvoiceItem> items) {
        this.id = id;
        this.invoiceDate = invoiceDate;
        this.storeName = storeName;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.imageUrl = imageUrl;
        this.returnDays = returnDays;
        this.items = items;
        this.status = status;
        this.vatAmount = vatAmount;
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * Getter and Setter
     */
    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getReturnDays() {
        return returnDays;
    }

    public void setReturnDays(Integer returnDays) {
        this.returnDays = returnDays;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }
}