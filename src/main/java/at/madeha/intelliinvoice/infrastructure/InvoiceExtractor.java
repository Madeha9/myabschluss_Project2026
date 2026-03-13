package at.madeha.intelliinvoice.infrastructure;

import at.madeha.intelliinvoice.business.Invoice;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.quarkiverse.langchain4j.ImageUrl;
import io.quarkiverse.langchain4j.RegisterAiService;
// ... other imports

@RegisterAiService
public interface InvoiceExtractor {

    @SystemMessage("""
            You are a specialized OCR and accounting assistant.
            Extract data from the provided invoice image.
            Return ONLY valid JSON.
            """)
    @UserMessage("Process this invoice image: {{imageUrl}}")
    Invoice extract(@ImageUrl @V("imageUrl") String imageUrl);
}


