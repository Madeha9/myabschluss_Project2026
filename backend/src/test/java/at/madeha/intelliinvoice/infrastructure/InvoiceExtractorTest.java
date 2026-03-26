//package at.madeha.intelliinvoice.infrastructure;
//
//import io.quarkus.test.junit.QuarkusTest;
//import jakarta.inject.Inject;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@QuarkusTest // This starts the Quarkus container for the test
//public class InvoiceExtractorTest {
//
//    @Inject
//    InvoiceExtractor invoiceExtractor;
/// /
/// /    @Test
/// /    void testInvoiceExtraction() {
/// /        // Use the public sample URL from the internet
/// /        String url = "https://intelliinvoice-files-wifi-2026.s3.eu-north-1.amazonaws.com/12a67228-2481-4686-9dd2-fe19b9610f10_null";
/// /
/// /        /*Calling  the LLM Claude Sonnet to extract the info
/// /         * invoiceExtractor is the method of the llm in the interface
/// /         */
/// /        String result = invoiceExtractor.extract(url);
/// /        // Assertions
/// /        assertNotNull(result, "The LLM response should not be null");
/// /        assertFalse(result.isBlank(), "The LLM response should not be empty");
/// /        // 2. The "Smart" check, Does it contain a dollar/euro amount?
/// /        // This regex looks for a digit followed by a decimal and two more digits
/// /        //boolean containsPrice = result.matches(".*\\d+\\.\\d{2}.*");
/// /        // Check if the response contains specific words , close , shoes etcc...
/// /        assertTrue(result.toLowerCase().contains("invoice"), "Response should contain the word 'invoice'");
/// /
/// /        System.out.println("JUnit Test Result: " + result);
/// /    }
//
//    @Test
//    void testInvoiceExtraction2() {
//        // Use the public sample URL from the internet with more details toc hch if the aı an reconginze it
//        String url = "https://intelliinvoice-files-wifi-2026.s3.eu-north-1.amazonaws.com/7cc4e5ff-5f8d-4a12-975d-00311ff748e0_null";
//
//        /*Calling  the LLM Claude Sonnet to extract the info
//         * invoiceExtractor is the method of the llm in the interface
//         */
//        String result = invoiceExtractor.extract(url);
//        // Assertions
//        assertNotNull(result, "The LLM response should not be null");
//        assertFalse(result.isBlank(), "The LLM response should not be empty");
//        // 2. The "Smart" check, Does it contain a dollar/euro amount?
//        // This regex looks for a digit followed by a decimal and two more digits
//        //boolean containsPrice = result.matches(".*\\d+\\.\\d{2}.*");
//        // Check if the response contains specific words , close , shoes etcc...
//        assertTrue(result.toLowerCase().contains("invoice"), "Response should contain the word 'invoice'");
//
//        System.out.println("JUnit Test Result: " + result);
//    }
//}