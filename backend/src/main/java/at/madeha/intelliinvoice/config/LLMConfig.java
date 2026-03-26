package at.madeha.intelliinvoice.config;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LLMConfig {

    public String getModelName() {
        return "claude-3-sonnet";
    }

}