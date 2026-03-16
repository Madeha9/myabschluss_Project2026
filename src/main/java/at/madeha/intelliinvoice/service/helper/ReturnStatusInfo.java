package at.madeha.intelliinvoice.service.helper;

import at.madeha.intelliinvoice.business.InvoiceStatus;

/*
a helper Class to set the invoice status and the day left to return the invice
 */
public record ReturnStatusInfo(InvoiceStatus status, long daysLeft) {
}