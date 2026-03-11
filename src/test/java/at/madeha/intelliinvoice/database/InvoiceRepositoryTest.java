package at.madeha.intelliinvoice.database;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class InvoiceRepositoryTest {
    /*we can not create new InvoiceRepo because it has EntityManger that is created by Quarkus
     * so using Injection to  tell quarks please call the entity manger tool for database , so that we can do our
     * database methode like delete etc
     */

    @Inject
    InvoiceRepository invoiceRepo;

    //to clean the database after each test
//    @BeforeEach
//    @Transactional
//    public void cleanDatabase() {
//     invoiceRepo.deleteAll();
//
//    }
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

        // create first invoice
        InvoiceEntity invoice1 = new InvoiceEntity();
        invoice1.setStoreName("Store1");
        invoice1.setInvoiceDate(LocalDate.now());
        invoice1.setTotalAmount(new BigDecimal("13.00"));
        invoice1.setCurrency("USD");
        invoice1.setCreatedAt(Instant.now());
        invoice1.setUpdatedAt(Instant.now());
        invoiceRepo.save(invoice1);

        // create second invoice
        InvoiceEntity invoice2 = new InvoiceEntity();
        invoice2.setStoreName("Store2");
        invoice2.setInvoiceDate(LocalDate.now());
        invoice2.setTotalAmount(new BigDecimal("20.00"));
        invoice2.setCurrency("EUR");
        invoice2.setCreatedAt(Instant.now());
        invoice2.setUpdatedAt(Instant.now());
        invoiceRepo.save(invoice2);
        invoice1.setReturnDays(14);


        // test
        List<InvoiceEntity> invoices = invoiceRepo.findAll();
        //the database should have at leat two line that has beingn ases
        assertTrue(invoices.size() >= 2, "The database should contain exactly 2 invoices");
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