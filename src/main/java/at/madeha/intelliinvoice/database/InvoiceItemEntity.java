package at.madeha.intelliinvoice.database;
//gives the  access to JPA annotations like @Entity , @Table etc.... without will not  work

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "invoice_item")
public class InvoiceItemEntity {
    @Id //tells hibernate this is a primary key of the table
    @GeneratedValue(strategy = GenerationType.UUID) //The ID should be generated automatically
    private UUID id; // Every invoice gets a unique identifier
    /*
    optional = false  each item must belong to an invoice , can not be without invoice
    fetch is to load the info of an invoice for the item , fetchtyope.lazy download the invoice for the item only when
    it needed . if ı open the item info , hibernate will not download the invoice for that item automatically
    unless ı call item.getinvoice , item the invoice list
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    /*
    create the column invoice_id as  a join column   to connect the item table to the inovice ,
     this a foreign key , can not be null
     */
    @JoinColumn(name = "invoice_id", nullable = false)
    //to connect the item to the invoice , a reference to the parent invoice class
    private InvoiceEntity invoice;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "quantity", precision = 12, scale = 2)
    private BigDecimal quantity;

    @Column(name = "unit_price", precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "line_total", precision = 12, scale = 2)
    private BigDecimal lineTotal;

    public InvoiceItemEntity() {
    }

    //getter and setter
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

}