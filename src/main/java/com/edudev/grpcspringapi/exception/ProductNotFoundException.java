package com.edudev.grpcspringapi.exception;

import io.grpc.Status;

import static io.grpc.Status.NOT_FOUND;

public class ProductNotFoundException extends BaseBusinessException {

    private static final String ERROR_MESSAGE = "Product with ID %s wasn't found.";
    private final Long id;

    public ProductNotFoundException(Long id) {
        super(String.format(ERROR_MESSAGE, id));
        this.id = id;
    }

    @Override
    public Status getStatusCode() {
        return NOT_FOUND;
    }

    @Override
    public String getErrorMessage() {
        return String.format(ERROR_MESSAGE, this.id);
    }
}
