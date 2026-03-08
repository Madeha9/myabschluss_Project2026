package at.madeha.intelliinvoice.service;

import at.madeha.intelliinvoice.database.InvoiceEntity;
import at.madeha.intelliinvoice.database.InvoiceItemEntity;
import at.madeha.intelliinvoice.exception.InvoiceValidationException;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class InvoiceValidationService {

    public void validate(InvoiceEntity invoice) {
        validateInvoiceFields(invoice);
        //  Validate invoice fields
        validateInvoiceItems(invoice);
        // Validate items and totals
    }

    //  Validate main invoice fields
    //to avoid the null invoice , or return null or null-pointerException
    private void validateInvoiceFields(InvoiceEntity invoice) {
        if (invoice == null) {
            throw new InvoiceValidationException("Invoice cannot be null");
        }

        if (invoice.getStoreName() == null || invoice.getStoreName().isBlank()) {
            throw new InvoiceValidationException("Store name is required");
        }

        if (invoice.getInvoiceDate() == null) {
            throw new InvoiceValidationException("Invoice date is required");
        }

        if (invoice.getTotalAmount() == null || invoice.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvoiceValidationException("Total amount must be greater than zero");
        }

        if (invoice.getCurrency() == null || invoice.getCurrency().length() != 3) {
            throw new InvoiceValidationException("Currency must be 3 letters");
        }
    }

    // Validate items and check totals
    private void validateInvoiceItems(InvoiceEntity invoice) {
        //İnvoice is connected to the İtem table using the OntoMany relationship
        List<InvoiceItemEntity> items = invoice.getItems();
        //no one can have an invoice without buying anything
        if (items == null || items.isEmpty()) {
            throw new InvoiceValidationException("Invoice must have at least one item");
        }
       /*Big Decimals to store the decimals exactly , not like double, using double will get like 0.899999 ,
        but using bigDecimal for money because the result will be like 0.90 is good to work with
        */

        // we can not define sum = 0 , using BigDecimal supposed always to be BigDecimal.ZERO
        BigDecimal sum = BigDecimal.ZERO;
        for (InvoiceItemEntity item : items) {
            if (item.getQuantity() == null || item.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                throw new InvoiceValidationException("Item quantity must be positive");
            }

            if (item.getUnitPrice() == null || item.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new InvoiceValidationException("Item unit price must be positive");
            }
            BigDecimal lineTotal = item.getQuantity().multiply(item.getUnitPrice());
            sum = sum.add(lineTotal);
            //the total of the items cost
            item.setLineTotal(lineTotal);

        }

        if (sum.compareTo(invoice.getTotalAmount()) != 0) {
            throw new InvoiceValidationException("Invoice total does not match sum of items");
        }
    }
}

/*
import at.madeha.intelliinvoice.utilities.DateUtils;
import at.madeha.intelliinvoice.utilities.ValidationUtils;

public class InvoiceService {

    public void validateInvoiceData(String storeName, BigDecimal totalAmount) {
        // Using ValidationUtils
        if (ValidationUtils.isEmpty(storeName)) {
            throw new IllegalArgumentException("Store name cannot be empty");
        }

        if (!ValidationUtils.isPositive(totalAmount.doubleValue())) {
            throw new IllegalArgumentException("Total amount must be positive");
        }
    }

    public void printInvoiceDate(LocalDate date) {
        // Using DateUtils
        String formatted = DateUtils.formatDate(date);
        System.out.println("Invoice date: " + formatted);
    }
}
 */