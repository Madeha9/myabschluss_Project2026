package at.madeha.intelliinvoice.business;

/**
 * Represents the status of an invoice to ensure data consistency.
 * This enum restricts the available options for invoice states,
 * preventing manual entry errors and defining the return lifecycle.
 */
public enum InvoiceStatus {
    /**
     * The invoice is currently within the allowed return period.
     */
    RETURNABLE,
    /**
     * The return period has expired or the items cannot be returned.
     */
    NON_RETURNABLE,
    /** The purchase is completed and the user is satisfied. */
    SATISFIED
}
