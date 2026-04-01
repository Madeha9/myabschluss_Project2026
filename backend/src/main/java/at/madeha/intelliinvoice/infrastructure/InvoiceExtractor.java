package at.madeha.intelliinvoice.infrastructure;

import at.madeha.intelliinvoice.business.Invoice;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.quarkiverse.langchain4j.ImageUrl;
import io.quarkiverse.langchain4j.RegisterAiService;

/**
 * AI Service interface using LangChain4j and Claude (Anthropic)  to extract the invoice data.
 * This service acts as the bridge between our application and the LLM
 * to perform intelligent data extraction from images.
 */

/*
 * We use an interface because LangChain4j automatically generates
 * the implementation for us at runtime.
 */
@RegisterAiService
public interface InvoiceExtractor {
    /**
     * The System Message sets the Persona(restricted rules ) and rules for the AI.
     * It forces the AI to act like an accountant and strictly return JSON.
     */
    @SystemMessage("""
            You are a specialized OCR and accounting assistant.
            Extract data from the provided invoice image.
            Return ONLY valid JSON.
            """)
    /*
     * The UserMessage sends the specific image URL to the AI.
     * LangChain4j will download the image from S3 and send it to Claude.
     */
    @UserMessage("Process this invoice image: {{imageUrl}}")
    Invoice extract(@ImageUrl @V("imageUrl") String imageUrl);
}


