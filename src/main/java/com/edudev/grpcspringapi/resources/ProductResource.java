package com.edudev.grpcspringapi.resources;

import com.edudev.grpcspringapi.ProductRequest;
import com.edudev.grpcspringapi.ProductResponse;
import com.edudev.grpcspringapi.ProductServiceGrpc.ProductServiceImplBase;
import com.edudev.grpcspringapi.dto.ProductInputDTO;
import com.edudev.grpcspringapi.services.IProductService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ProductResource extends ProductServiceImplBase {

    private IProductService productService;

    public ProductResource(IProductService productService) {
        this.productService = productService;
    }

    @Override
    public void create(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {

        var input = new ProductInputDTO(request.getName(), request.getPrice(), request.getStockQuantity());

        var product = productService.create(input);

        var response = ProductResponse.newBuilder()
                .setId(product.id())
                .setName(product.name())
                .setPrice(product.price())
                .setStockQuantity(product.quantityInStock())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
