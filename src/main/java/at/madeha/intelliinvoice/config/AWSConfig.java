package at.madeha.intelliinvoice.config;

import jakarta.enterprise.context.ApplicationScoped;

/*
instead of coding the value harder in the cloudStorageService , we use small class for it , so that changing the info,
* need only to update this class
 */
@ApplicationScoped
public class AWSConfig {

    public String getBucketName() {
        return "intelliinvoice-files-wifi-2026";
    }

    public String getRegion() {
        return "eu-north-1";
    }

}