package at.madeha.intelliinvoice.business;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/*
represents the   as a plain Java object (without JPA) database and the framework

 */
public class Invoice {

    private UUID id;
    private LocalDate invoiceDate;
    private String storeName;
    private BigDecimal totalAmount;
    private String currency;
    private String imageUrl;
    private Integer returnDays;
    private InvoiceStatus status;
    private List<InvoiceItem> items;

    // Constructors
    public Invoice() {
    }

    public Invoice(UUID id, LocalDate invoiceDate, String storeName,
                   BigDecimal totalAmount, String currency, InvoiceStatus status,
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
    }

    // Getters and Setters
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