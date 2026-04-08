package at.madeha.intelliinvoice.database;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static io.smallrye.common.constraint.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class InvoiceRepositoryTest {
    /*we can not create new InvoiceRepo because it has EntityManger that is created by Quarkus
     * so using Injection to  tell quarks please call the entity manger tool for database , so that we can do our
     * database methode like delete etc
     */

    @Inject
    InvoiceRepository invoiceRepo;

    //to clean the database after each test
    @BeforeEach
    @Transactional
    public void cleanDatabase() {
        invoiceRepo.deleteAll();

    }

    @Test
    void save() {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setInvoiceDate(LocalDate.now());
        invoiceEntity.setStoreName("Test Store");
        invoiceEntity.setTotalAmount(new BigDecimal("10.50"));
        invoiceEntity.setCurrency("EUR");
        invoiceEntity.setReturnDays(14);
        invoiceEntity.setCreatedAt(Instant.now());
        invoiceEntity.setUpdatedAt(Instant.now());
        InvoiceEntity newInvoice = invoiceRepo.save(invoiceEntity);
        assertNotNull(newInvoice.getId());
        UUID id = newInvoice.getId();


    }

    @Test
    void saveWithItems() {
        // Using the static method to build the object (Includes 1 item inside), from the Utilities Class
        InvoiceEntity invoice = InvoiceTestUtils.createInvoice("Test Store", "100.00");
        //Save it using the repository
        InvoiceEntity saved = invoiceRepo.save(invoice);
        //Assertions
        assertNotNull(saved.getId(), "Invoice ID should not be null");
        // Verify the items list isn't empty
        assertTrue(saved.getItems().size() > 0);// Verify the first item in the list was actually saved with its own ID
        assertNotNull(saved.getItems().get(0).getId(), "Item ID should not be null");
        // Verify the size is correct
        assertTrue(saved.getItems().size() > 0);
    }

    @Test
    void findById() {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setInvoiceDate(LocalDate.now());
        invoiceEntity.setStoreName("FindTest Store");
        invoiceEntity.setTotalAmount(new BigDecimal("15.50"));
        invoiceEntity.setCurrency("USD");
        invoiceEntity.setReturnDays(14);
        invoiceEntity.setCreatedAt(Instant.now());
        invoiceEntity.setUpdatedAt(Instant.now());
        InvoiceEntity newInvoice = invoiceRepo.save(invoiceEntity);
        UUID id = newInvoice.getId();
        assertTrue(invoiceRepo.findById(id).isPresent());

    }

    @Test
    void findAll() {
        // Use the Utility class to build the objects
        InvoiceEntity inv1 = InvoiceTestUtils.createInvoice("Store A", "10.00");
        InvoiceEntity inv2 = InvoiceTestUtils.createInvoice("Store B", "20.00");

        // Save them using the injected repo
        invoiceRepo.save(inv1);
        invoiceRepo.save(inv2);

        List<InvoiceEntity> invoices = invoiceRepo.findAll();

        // With @BeforeEach cleaning the DB, this will be exactly 2
        assertTrue(invoices.size() >= 2);
        assertFalse(invoices.get(0).getItems().isEmpty());
    }

    @Test
    void findByStoreName() {
        String target = "SpecificStore";
        invoiceRepo.save(InvoiceTestUtils.createInvoice(target, "50.00"));
        invoiceRepo.save(InvoiceTestUtils.createInvoice("Other", "10.00"));

        List<InvoiceEntity> results = invoiceRepo.findAll().stream()
                .filter(i -> target.equals(i.getStoreName()))
                .toList();

        assertTrue(results.size() >= 1);
        assertEquals(target, results.get(0).getStoreName());
    }


    @Test
    void deleteById() {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setInvoiceDate(LocalDate.now());
        invoiceEntity.setStoreName("FindTest Store");
        invoiceEntity.setTotalAmount(new BigDecimal("15.50"));
        invoiceEntity.setCurrency("USD");
        invoiceEntity.setReturnDays(16);
        invoiceEntity.setCreatedAt(Instant.now());
        invoiceEntity.setUpdatedAt(Instant.now());
        InvoiceEntity newInvoice = invoiceRepo.save(invoiceEntity);
        UUID id = newInvoice.getId();
        invoiceRepo.deleteById(id);
        assertTrue(invoiceRepo.findById(id).isEmpty());
    }
}