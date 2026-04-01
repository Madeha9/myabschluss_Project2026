package at.madeha.intelliinvoice.exception;

/**
 * Custom Exception for handling failures during the file upload process to Cloud Storage (AWS S3).
 * Extends RuntimeException so it doesn't force every method to use a 'throws' clause.
 */
public class FileUploadException extends RuntimeException {
    // We assign the specific ErrorCode from our Enum here.
    private final ErrorCode errorCode = ErrorCode.FILE_UPLOAD_FAILED;

    /**
     * Constructor for the exception.
     *
     * @param message A human-readable description of the error.
     * @param cause   The underlying technical reason (e.g., a network timeout).
     */

    /*
     * We do not have to define the error code in the constructor here,
     * because we assigned the value 'FILE_UPLOAD_FAILED' already.
     */
    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    /** @return the pre-defined ErrorCode associated with this exception. */
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}