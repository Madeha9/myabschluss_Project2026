package at.madeha.intelliinvoice.infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * DEVELOPMENT LOG / INTEGRATION TEST:
 * This class was used during the development phase to verify the connection
 * between Quarkus and the Claude AI (Anthropic).
 * * It serves as proof of integration testing, ensuring that image data from
 * S3 is correctly processed by the LLM before the full business logic was built.
 */
@ApplicationScoped
public class LlmTest {

    @Inject
    InvoiceExtractor invoiceExtractor; //the interface that call the llm model

    /**
     * This method tells Quarkus: "As soon as the application starts,
     * run the runTest() method automatically."
     */
//    void onStart(@Observes StartupEvent ev) {
//        runTest();
//    }

//    public void runTest() {
//        //the url of the image
//        String sampleImageUrl = "https://intelliinvoice-files-wifi-2026.s3.eu-north-1.amazonaws.com/7cc4e5ff-5f8d-4a12-975d-00311ff748e0_null";
//         result = invoiceExtractor.extract(sampleImageUrl);
//        // This will throw an error if the LLM returns nothing
//        assert result != null && !result.isBlank() : "LLM returned an empty response!";
//        System.out.println("LLM Response: " + result);
//    }
}
