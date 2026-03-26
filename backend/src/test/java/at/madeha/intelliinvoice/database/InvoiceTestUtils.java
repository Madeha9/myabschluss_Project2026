//package at.madeha.intelliinvoice.database;
//
//import java.math.BigDecimal;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//public class InvoiceTestUtils {
//
//    /**
//     * Creates a fully populated InvoiceEntity with one item, so thst ı can call it and use it for the test
//     * We don't save it here so the test can decide when to save.
//     */
//    public static InvoiceEntity createInvoice(String name, String amount) {
//        InvoiceEntity invoice = new InvoiceEntity();
//        invoice.setStoreName(name);
//        invoice.setTotalAmount(new BigDecimal(amount));
//        invoice.setInvoiceDate(LocalDate.now());
//        invoice.setCurrency("EUR");
//        invoice.setCreatedAt(Instant.now());
//        invoice.setUpdatedAt(Instant.now());
//
//        InvoiceItemEntity item = new InvoiceItemEntity();
//        item.setDescription("Item for " + name);
//        item.setQuantity(BigDecimal.valueOf(1));
//        item.setUnitPrice(new BigDecimal(amount));
//        item.setLineTotal(new BigDecimal(amount));
//
//        // The link between invoice and each item
//        item.setInvoice(invoice);
//        invoice.setItems(new ArrayList<>(List.of(item)));
//
//        return invoice;
//    }
//}