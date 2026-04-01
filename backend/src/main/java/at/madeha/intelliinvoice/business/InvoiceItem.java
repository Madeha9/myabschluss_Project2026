package at.madeha.intelliinvoice.business;
import java.math.BigDecimal;

/**
 * Represents an individual line item from an invoice.
 * This POJO is used to capture specific product details extracted by the AI.
 */
public class InvoiceItem {

    private String description;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    /**
     * The total price for this line (quantity * unitPrice).
     */
    private BigDecimal lineTotal;

    /**
     * Default constructor for framework (Quarkus) mapping.
     */
    public InvoiceItem() {
    }

    /**
     * Full constructor for manual initialization.
     * * @param description the name or detail of the product/service
     * @param quantity    the number of units purchased
     * @param unitPrice   the cost per single unit
     * @param lineTotal   the calculated total for this specific line
     */

    public InvoiceItem(String description, BigDecimal quantity,
                       BigDecimal unitPrice, BigDecimal lineTotal) {
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = lineTotal;
    }

    // Getters and Setters
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