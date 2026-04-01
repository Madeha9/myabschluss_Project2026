package at.madeha.intelliinvoice.service.helper;

import at.madeha.intelliinvoice.business.InvoiceStatus;

/**
 * Immutable data container for return logic results.
 * A clean way to pass both the Enum status and the numeric countdown
 * between services without needing boilerplate getters or setters.
 */
/*
a helper Class as Record only to hall the data  to set the invoice status and the day left to return the invoice ,  it is  the Class record only to handle data
no methods or calculations , no need to write getter nas setter and Constructor  or to string etc... only data
 */
public record ReturnStatusInfo(InvoiceStatus status, long daysLeft) {
}