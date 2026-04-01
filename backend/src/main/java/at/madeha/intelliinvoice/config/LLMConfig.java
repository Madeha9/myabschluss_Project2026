package at.madeha.intelliinvoice.config;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Configuration for the Large Language Model (LLM) settings.
 * Centralizes the model selection to allow for easy updates
 * and testing of different AI versions.
 */
@ApplicationScoped
public class LLMConfig {
    /**
     * @return the specific version of the AI model used for extraction
     */
    public String getModelName() {
        return "claude-3-sonnet";
    }

}