package at.madeha.intelliinvoice.service;

import at.madeha.intelliinvoice.database.InvoiceEntity;
import at.madeha.intelliinvoice.database.InvoiceItemEntity;
import at.madeha.intelliinvoice.database.InvoiceRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class InvoiceReturnServiceTest {

    @Inject
    InvoiceReturnService returnService;
    @Inject
    InvoiceRepository invoiceRepository;
    @Inject
    InvoiceProcessingService processingService;

    @BeforeEach
    @Transactional
    @Test
    void setUpReturndays() {
        InvoiceEntity testInvoice = new InvoiceEntity();
        testInvoice.setStoreName("Test Store");
        testInvoice.setInvoiceDate(LocalDate.now());
        testInvoice.setCurrency("USD");
        testInvoice.setTotalAmount(new BigDecimal("300"));
        testInvoice.setCreatedAt(Instant.now());
        testInvoice.setUpdatedAt(Instant.now());
        testInvoice.setReturnDays(14);
        InvoiceItemEntity item = new InvoiceItemEntity();
        item.setDescription("Test Item");
        item.setQuantity(BigDecimal.valueOf(1));
        item.setUnitPrice(new BigDecimal("300"));

        testInvoice.setItems(List.of(item));
        processingService.createInvoice(testInvoice);
        String message = returnService.checkReturnDays(testInvoice);
        assertTrue(message.contains("You can return"));
    }

    @Test
    void testCalculateMonthlyTotal() {
        // Assuming DB already has invoices for the current month
        BigDecimal total = returnService.calculateMonthlyTotal(
                LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        assertNotNull(total);
        assertTrue(total.compareTo(BigDecimal.ZERO) >= 0);
    }
}