package com.plexus.w2m.exception;

public class ServiceException extends RuntimeException {
    private int code = 0;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(int code) {
        this.code = code;
    }

    public ServiceException(String message, Exception e) {
        super(message, e);
    }

    public ServiceException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message, Exception e, int code) {
        super(message, e);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
