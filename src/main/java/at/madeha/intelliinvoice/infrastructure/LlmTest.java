package at.madeha.intelliinvoice.infrastructure;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class LlmTest {

    @Inject
    InvoiceExtractor invoiceExtractor; //the interface that call the llm model

    /**
     * This method tells Quarkus: "As soon as the application starts,
     * run the runTest() method automatically."
     */
    void onStart(@Observes StartupEvent ev) {
        runTest();
    }

    public void runTest() {
        //the url of the image
        String sampleImageUrl = "https://intelliinvoice-files-wifi-2026.s3.eu-north-1.amazonaws.com/7cc4e5ff-5f8d-4a12-975d-00311ff748e0_null";
        String result = invoiceExtractor.extract(sampleImageUrl);
        // This will throw an error if the LLM returns nothing
        assert result != null && !result.isBlank() : "LLM returned an empty response!";
        System.out.println("LLM Response: " + result);
    }
}
