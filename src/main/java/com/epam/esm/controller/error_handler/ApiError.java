package com.epam.esm.controller.error_handler;

/**
 * Class represent error of rest service request
 */
public class ApiError {

    private String errorMessage;
    private int errorCode;

    /**
     * Instantiates a new Api error.
     */
    public ApiError() {
    }

    /**
     * Instantiates a new Api error.
     *
     * @param errorMessage the error message
     * @param errorCode    the error code
     */
    public ApiError(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets error message.
     *
     * @param errorMessage the error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets error code.
     *
     * @return the error code
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Sets error code.
     *
     * @param errorCode the error code
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
