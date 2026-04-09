/**
 * Isolated logic tests for the core business services.
 * This package verifies that individual service workflows and data
 * transformations are accurate before they are integrated with other modules.
 */
//package at.madeha.intelliinvoice.service;
//
//import at.madeha.intelliinvoice.database.InvoiceEntity;
//import at.madeha.intelliinvoice.database.InvoiceItemEntity;
//import io.quarkus.test.junit.QuarkusTest;
//import jakarta.inject.Inject;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@QuarkusTest
//public class InvoiceProcessingServiceTest {
//
//    @Inject
//    //Quarkus creates this Automatic
//    InvoiceProcessingService processingService;
//
//    private InvoiceEntity testInvoice;
//
//    /*
//    using beforeEach to run this block of the code before running the test , the method setup will
//    run before starting any other test
//    *Transactional is to delete the database test data
//     */
//    @BeforeEach
//    @Transactional
//    void setUp() {
//        testInvoice = new InvoiceEntity();
//        testInvoice.setStoreName("Test Store");
//        testInvoice.setInvoiceDate(LocalDate.now());
//        testInvoice.setCurrency("USD");
//        testInvoice.setTotalAmount(new BigDecimal("300"));
//        testInvoice.setCreatedAt(Instant.now());
//        testInvoice.setImageUrl("https://www.aiseesoft.com/tutorial/jpg-to-url.html");
//        testInvoice.setUpdatedAt(Instant.now());
//        testInvoice.setReturnDays(14);
//
//        InvoiceItemEntity item = new InvoiceItemEntity();
//        item.setDescription("Test Item");
//        item.setQuantity(new BigDecimal("2"));
//        item.setUnitPrice(new BigDecimal("150"));
//        item.setInvoice(testInvoice);
//        testInvoice.setItems(List.of(item));
//        processingService.createInvoice(testInvoice);
//    }
//
//    @Test //this is a test method
//    @Transactional
//        //clean the databse after the test
//    void testCreateInvoice() {
//        assertNotNull(testInvoice.getId());
//        assertEquals(1, testInvoice.getItems().size());
//        assertEquals(new BigDecimal("300"), testInvoice.getTotalAmount());
//    }
//
//    @Test
//    @Transactional
//    void testListInvoicestotalamount() {
//        processingService.listInvoicesWithReturnInfo();
//        assertTrue(testInvoice.getTotalAmount().compareTo(BigDecimal.valueOf(200)) > 0);
//    }
//
//    @Test
//    @Transactional
//    void testListInvoicesWithReturnInfodays() {
//        processingService.listInvoicesWithReturnInfo();
//        assertNotNull(testInvoice.getReturnDays(), "Return days not found");
//    }
//
//
//    @Test
//    @Transactional
//    void testDeleteInvoice() {
//        UUID id = testInvoice.getId();
//        processingService.deleteInvoice(id);
//        RuntimeException ex = assertThrows(RuntimeException.class,
//                () -> processingService.deleteInvoice(id));
//        assertEquals("Invoice not found", ex.getMessage());
//    }
//}