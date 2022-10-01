package com.edudev.grpcspringapi.exception;

import io.grpc.Status;

import static io.grpc.Status.ALREADY_EXISTS;

public class ProductAlreadyExistsException extends BaseBusinessException {

    private static final String ERROR_MESSAGE = "Product %s already exists in the system.";
    private final String name;

    public ProductAlreadyExistsException(String name) {
        super(String.format(ERROR_MESSAGE, name));
        this.name = name;
    }

    @Override
    public Status getStatusCode() {
        return ALREADY_EXISTS;
    }

    @Override
    public String getErrorMessage() {
        return String.format(ERROR_MESSAGE, this.name);
    }
}
