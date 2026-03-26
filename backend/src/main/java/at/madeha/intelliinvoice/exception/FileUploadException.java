package at.madeha.intelliinvoice.exception;

/*
this is class to handle the error for the upload the file
 */
public class FileUploadException extends RuntimeException {
    private final ErrorCode errorCode = ErrorCode.FILE_UPLOAD_FAILED;

    //we do not have to define the error code in the constructor here , because we assign the value already
    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    //to return the error code
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}