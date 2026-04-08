package at.madeha.intelliinvoice.service;

import at.madeha.intelliinvoice.database.InvoiceEntity;
import at.madeha.intelliinvoice.database.InvoiceItemEntity;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class InvoiceValidationServiceTest {

    @Inject
    InvoiceValidationService validationService;

    @Test
    void testValidInvoice() {
        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setStoreName("Test Store");
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setCurrency("USD");
        invoice.setTotalAmount(new BigDecimal("200"));

        InvoiceItemEntity item = new InvoiceItemEntity();
        item.setDescription("Item 1");
        item.setQuantity(new BigDecimal("2"));
        item.setUnitPrice(new BigDecimal("100"));
        item.setInvoice(invoice);

        invoice.setItems(List.of(item));

        assertDoesNotThrow(() -> validationService.validate(invoice));
    }

    @Test
    void testInvalidInvoice() {
        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setStoreName(""); // invalid
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setCurrency("USD");
        invoice.setTotalAmount(new BigDecimal("100"));

        InvoiceItemEntity item = new InvoiceItemEntity();
        item.setDescription("Item 1");
        item.setQuantity(new BigDecimal("1"));
        item.setUnitPrice(new BigDecimal("100"));
        item.setInvoice(invoice);

        invoice.setItems(List.of(item));

        Exception ex = assertThrows(RuntimeException.class, () -> validationService.validate(invoice));
        assertTrue(ex.getMessage().contains("Store name is required"));
    }
}