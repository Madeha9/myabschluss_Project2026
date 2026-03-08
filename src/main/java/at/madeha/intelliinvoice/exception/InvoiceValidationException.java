package at.madeha.intelliinvoice.exception;

public class InvoiceValidationException extends RuntimeException {

    public InvoiceValidationException() {
        super();
    }

    public InvoiceValidationException(String message) {
        super(message);
    }

    public InvoiceValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
/*
to use the enum error code
public class InvoiceValidationException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvoiceValidationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
 */