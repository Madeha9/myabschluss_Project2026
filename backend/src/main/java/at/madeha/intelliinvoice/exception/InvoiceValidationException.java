package at.madeha.intelliinvoice.exception;
/*
this is a custom error class that will handle the errors and the exceptions in the  project using a enum class
 */
public class InvoiceValidationException extends RuntimeException {
    private final ErrorCode errorCode;
    /*errorCode is final which means we need to  assign the value in constructor directly or giving it a value , and also
    the value can not be changed
     */


    // Standard constructor with code and message, in order to assign  the value of the error code
    public InvoiceValidationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    // Constructor for when another exception (cause) triggered this one
    public InvoiceValidationException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}