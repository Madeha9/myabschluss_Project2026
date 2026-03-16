package at.madeha.intelliinvoice.restapi;

/*
This is DTO , data transfer object ,is a Class only to format the data for thr GUI.
 */
public class InvoiceResponseDTO {
    public java.util.UUID id;
    public String storeName;
    public java.time.LocalDate invoiceDate;
    public java.math.BigDecimal totalAmount;
    public String status;
    public long daysLeft;

    // Constructor that takes the Entity and the Status Record
    public InvoiceResponseDTO(at.madeha.intelliinvoice.database.InvoiceEntity entity,
                              at.madeha.intelliinvoice.service.helper.ReturnStatusInfo info) {
        this.id = entity.getId();
        this.storeName = entity.getStoreName();
        this.invoiceDate = entity.getInvoiceDate();
        this.totalAmount = entity.getTotalAmount();
        this.status = info.status().name();
        this.daysLeft = info.daysLeft();
    }
}