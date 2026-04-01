package at.madeha.intelliinvoice.exception;

/**
 * Enumeration of custom error codes used throughout the application.
 * Centralizing error messages ensures that the Frontend (Web)
 * can show consistent messages to the user.
 */
public enum ErrorCode {
    /**
     * Returned when a requested Invoice ID does not exist in the database.
     */
    INVOICE_NOT_FOUND,
    /** Used when the AI extraction has missing required fields. */
    INVALID_INVOICE_DATA,
    /** Triggered if there is a connection issue with the AWS S3 Bucket. */
    FILE_UPLOAD_FAILED,
    /** A general code for when the Database (PostgreSQL) has a connection issue. */
    DATABASE_ERROR,
    /** Used if a user tries to upload something that isn't a PDF or Image. */
    INVALID_FILE_FORMAT
}
