
package at.madeha.intelliinvoice.database;
/*
map the objects to the database , coupled with the framework
 */
import at.madeha.intelliinvoice.business.InvoiceStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "invoice")
public class InvoiceEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "store_name", length = 255)
    private String storeName;

    @Column(name = "total_amount", precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "currency", length = 3)
    private String currency;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "return_days")
    private Integer returnDays;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(
            mappedBy = "invoice",
            //when  deleting or saving   the invoice, Hibernate will automatically do the same for the items.
            cascade = CascadeType.ALL,
            //removing one item from the list , will be removed from the database as well
            orphanRemoval = true
    )
    //this  to list the items of each invoice
    private List<InvoiceItemEntity> items;

    // Constructors
    public InvoiceEntity() {
    }
    /*
     @Enumerated(EnumType.STRING)
       @Column(name = "status")
    private InvoiceStatus status;
     */

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
}