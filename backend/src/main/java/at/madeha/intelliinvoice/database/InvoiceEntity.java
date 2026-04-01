package at.madeha.intelliinvoice.database;

import at.madeha.intelliinvoice.business.InvoiceStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Database Entity representing the 'invoice' table.
 * Maps the application objects to the persistence layer using JPA/Hibernate.
 */
@Entity
@Table(name = "invoice")
public class InvoiceEntity {
    /*
ı have to write all the info by myself like the @column part then hibernate will only take care of creating the table in the
database
*/
    @Id // Tells Hibernate this is a primary key of the table
    @GeneratedValue  // The ID should be generated automatically
    private UUID id;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "store_name", length = 255)
    private String storeName;

    @Column(name = "total_amount", precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "currency", length = 3)
    private String currency;
    /**
     * Stored as TEXT to accommodate long cloud storage URLs.
     */
    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "return_days")
    private Integer returnDays;
        /* @Transient :  we use it so that we do not have to create a table/column  in the database
        it  tells Hibernate that  there is no column in the database  for this
           Jackson will s call the 'getDaysRemaining()' the result will be added  to the JSON response
        */

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    /** * Ensures the enum is stored as a String (e.g., 'RETURNABLE') in the DB
     * rather than an integer index.
     */
    @Enumerated(EnumType.STRING)
    /* using the annotation enum so that java knows that is an enum will not try  to use a number in the
    field as a status
     */
    @Column(name = "invoice_status")
    private InvoiceStatus status = InvoiceStatus.RETURNABLE;
    @Column(name = "vat_Amount")
    private BigDecimal vatAmount;
    @Column(name = "invoice_Number")
    private Integer invoiceNumber;
    // we set it to the default then will be changed according to the condiations in the ReturnService class
    /**
     * Relationship to individual line items.
     * ManagedReference prevents infinite recursion during JSON serialization.
     * CascadeType.ALL ensures items are saved/deleted along with the invoice.
     */
    @JsonManagedReference  //to avoid the infinite loop
    @OneToMany(
            mappedBy = "invoice",
            //when  deleting or saving   the invoice, Hibernate will automatically do the same for the items.
            cascade = CascadeType.ALL,
            //removing one item from the list , will be removed from the database as well
            orphanRemoval = true
    )
    //this  to list the items of each invoice
    private List<InvoiceItemEntity> items = new ArrayList<>(); // changed to the array list to avoid the null pointer

    // Constructor
    public InvoiceEntity() {
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<InvoiceItemEntity> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemEntity> items) {
        this.items = items;
    }

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
}