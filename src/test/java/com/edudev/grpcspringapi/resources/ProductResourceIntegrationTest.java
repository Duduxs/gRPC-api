package com.edudev.grpcspringapi.resources;

import com.edudev.grpcspringapi.ProductRequest;
import com.edudev.grpcspringapi.ProductResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static com.edudev.grpcspringapi.ProductServiceGrpc.ProductServiceBlockingStub;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext
public class ProductResourceIntegrationTest {

    @GrpcClient("inProcess")
    private ProductServiceBlockingStub service;

    @Test
    @DisplayName("Should be able to create a product")
    public void createProduct() {

        var productRequest = ProductRequest
                .newBuilder()
                .setName("product name")
                .setPrice(2.0)
                .setStockQuantity(9)
                .build();

        ProductResponse productResponse = service.create(productRequest);

        assertThat(productRequest)
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "price", "stockQuantity")
                .isEqualTo(productResponse);

    }

}
