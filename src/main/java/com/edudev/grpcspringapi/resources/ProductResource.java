package com.edudev.grpcspringapi.resources;

import com.edudev.grpcspringapi.*;
import com.edudev.grpcspringapi.ProductServiceGrpc.ProductServiceImplBase;
import com.edudev.grpcspringapi.dto.ProductInputDTO;
import com.edudev.grpcspringapi.services.IProductService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.stream.Collectors;

@GrpcService
public class ProductResource extends ProductServiceImplBase {

    private IProductService productService;

    public ProductResource(IProductService productService) {
        this.productService = productService;
    }

    @Override
    public void findById(RequestById request, StreamObserver<ProductResponse> responseObserver) {

        var product = productService.findById(request.getId());

        var productResponse = ProductResponse.newBuilder()
                .setId(product.id())
                .setName(product.name())
                .setPrice(product.price())
                .setStockQuantity(product.quantityInStock())
                .build();

        responseObserver.onNext(productResponse);
        responseObserver.onCompleted();

    }

    @Override
    public void findAll(EmptyRequest request, StreamObserver<ProductResponseList> responseObserver) {
        var output = productService.findAll()
                .stream()
                .map(product -> ProductResponse.newBuilder()
                        .setId(product.id())
                        .setName(product.name())
                        .setPrice(product.price())
                        .setStockQuantity(product.quantityInStock())
                        .build()
                ).collect(Collectors.toList());

        var products = ProductResponseList.newBuilder()
                .addAllData(output)
                .build();

        responseObserver.onNext(products);
        responseObserver.onCompleted();
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

    @Override
    public void deleteById(RequestById request, StreamObserver<EmptyResponse> responseObserver) {
        productService.deleteBy(request.getId());
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }
}
