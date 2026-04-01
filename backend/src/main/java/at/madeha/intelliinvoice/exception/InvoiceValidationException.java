package at.madeha.intelliinvoice.exception;

/**
 * Custom Exception for validation and business logic errors related to Invoices.
 * This class is flexible: it can hold different {@link ErrorCode} values depending on the failure.
 */

public class InvoiceValidationException extends RuntimeException {
    private final ErrorCode errorCode;

    /* * The ErrorCode is final, meaning it must be assigned in the constructor
     * and cannot be changed once the exception is created.
     */

    /**
     * Standard constructor to assign a specific ErrorCode and a descriptive message.
     *
     * @param errorCode The category of the error from our Enum.
     * @param message   Detailed explanation of what failed.
     */
    public InvoiceValidationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructor for when another technical exception (the 'cause') triggered this validation error.
     * @param errorCode The category of the error.
     * @param message Detailed explanation.
     * @param cause The original exception that was caught.
     */
    public InvoiceValidationException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /** @return the specific ErrorCode assigned to this validation failure. */
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}