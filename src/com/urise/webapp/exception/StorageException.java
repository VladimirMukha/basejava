package com.urise.webapp.exception;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String massage, String uuid, Exception e) {
        super(massage, e);
        this.uuid = uuid;
    }

    public StorageException(String message, Exception e) {
        this(message, null, e);
    }

    public StorageException(String massage) {
        this(massage, null, null);
    }

    public StorageException(Exception e) {
        this(e.getMessage(), e);
    }

    public String getUuid() {
        return uuid;
    }
}