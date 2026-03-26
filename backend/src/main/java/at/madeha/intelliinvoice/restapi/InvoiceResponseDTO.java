package at.madeha.intelliinvoice.restapi;

import at.madeha.intelliinvoice.database.InvoiceItemEntity;

import java.util.List;

/*
This is DTO , data transfer object ,is a Class only to format the data for thr GUI.
 */
public class InvoiceResponseDTO {
    public String imageUrl;
    public String currency;
    public java.util.UUID id;
    public String storeName;
    public java.time.LocalDate invoiceDate;
    public java.math.BigDecimal totalAmount;
    public String status;
    public long daysLeft;
    public java.math.BigDecimal vatAmount;
    public Integer invoiceNumber;
    public List<InvoiceItemEntity> items;


    // Constructor that takes the Entity and the Status Record
    public InvoiceResponseDTO(at.madeha.intelliinvoice.database.InvoiceEntity entity,
                              at.madeha.intelliinvoice.service.helper.ReturnStatusInfo info) {
        this.id = entity.getId();
        this.storeName = entity.getStoreName();
        this.invoiceDate = entity.getInvoiceDate();
        this.totalAmount = entity.getTotalAmount();
        this.status = info.status().name();
        this.daysLeft = info.daysLeft();
        this.invoiceNumber = entity.getInvoiceNumber();
        this.vatAmount = entity.getVatAmount();
        this.imageUrl = entity.getImageUrl();
        this.currency = entity.getCurrency();
        this.items = entity.getItems();
    }
}