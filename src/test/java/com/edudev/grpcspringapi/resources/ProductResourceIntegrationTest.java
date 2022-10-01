package com.edudev.grpcspringapi.resources;

import com.edudev.grpcspringapi.ProductRequest;
import com.edudev.grpcspringapi.ProductResponse;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static com.edudev.grpcspringapi.ProductServiceGrpc.ProductServiceBlockingStub;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext
public class ProductResourceIntegrationTest {

    @GrpcClient("inProcess")
    private ProductServiceBlockingStub service;

    @Autowired
    Flyway flyway;

    @BeforeEach
    public void clean() {
        flyway.clean();
        flyway.migrate();
    }

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

    @Test
    @DisplayName("Should not be able to create a product when we've a conflict between product's name (already save on db)")
    public void createProductAlreadyExists() {

        var productRequest = ProductRequest
                .newBuilder()
                .setName("Product B")
                .setPrice(2.0)
                .setStockQuantity(9)
                .build();

        assertThrows(
                StatusRuntimeException.class,
                () -> service.create(productRequest),
                "ALREADY_EXISTS: Product Product B already exists in the system."
        );

    }


}
