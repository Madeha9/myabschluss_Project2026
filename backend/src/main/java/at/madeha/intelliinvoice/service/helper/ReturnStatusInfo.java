package at.madeha.intelliinvoice.service.helper;

import at.madeha.intelliinvoice.business.InvoiceStatus;

/*
a helper Class as Record only to hall the data  to set the invoice status and the day left to return the invoice ,  it is  the Class record only to handle data
no methods or calculations , no need to write getter nas setter and Constructor  or to string etc... only data
 */
public record ReturnStatusInfo(InvoiceStatus status, long daysLeft) {
}