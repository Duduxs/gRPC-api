package com.edudev.grpcspringapi.resources;

import com.edudev.grpcspringapi.HelloReq;
import com.edudev.grpcspringapi.HelloRes;
import com.edudev.grpcspringapi.HelloServiceGrpc.HelloServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HelloResource extends HelloServiceImplBase {

    @Override
    public void hello(HelloReq request, StreamObserver<HelloRes> responseObserver) {

        var helloResponse = HelloRes.newBuilder()
                .setMessage(request.getMessage())
                .build();

        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }
}
