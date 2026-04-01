package at.madeha.intelliinvoice.restapi;

import at.madeha.intelliinvoice.database.InvoiceItemEntity;

import java.util.List;

/**
 * Data Transfer Object (DTO) designed specifically for the Frontend GUI.
 *  We use this DTO to decouple our Database Schema from the API.
 * It allows us to merge raw Invoice data with calculated "Return Status" logic
 * (like daysLeft) into a single JSON object that the Angular frontend can
 * easily display without doing its own complex calculations.
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

    /**
     * Maps a Database Entity and calculated Return Status into a flat
     * structure ready for JSON serialization.
     */
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