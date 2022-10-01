package com.edudev.grpcspringapi.exception;

import io.grpc.Status;

public abstract class BaseBusinessException extends RuntimeException {

    public BaseBusinessException(String msg) {
        super(msg);
    }

    public abstract Status getStatusCode();
    public abstract String getErrorMessage();

}
