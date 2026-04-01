package at.madeha.intelliinvoice.config;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Configuration class for AWS S3 settings.
 * Centralizes cloud storage details to avoid hardcoding values
 * throughout the service layer.
 */
@ApplicationScoped
public class AWSConfig {
    /**
     * @return the name of the S3 bucket used for invoice storage
     */
    public String getBucketName() {
        return "intelliinvoice-files-wifi-2026";
    }

    /** @return the AWS region where the bucket is hosted */
    public String getRegion() {
        return "eu-north-1";
    }

}