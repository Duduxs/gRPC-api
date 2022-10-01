package com.edudev.grpcspringapi.resources;

import com.edudev.grpcspringapi.*;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static com.edudev.grpcspringapi.ProductServiceGrpc.ProductServiceBlockingStub;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

    @Nested
    class Create {

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

    @Nested
    class FindById {

        @Test
        @DisplayName("Should be able to find a product with success")
        public void findProduct() {

            var productFindByIdRequest = RequestById.newBuilder().setId(1).build();

            var productFound = service.findById(productFindByIdRequest);

            assertThat(productFound.getId()).isEqualTo(1L);

        }

        @Test
        @DisplayName("Should be able to throw exception when product don't exists by id")
        public void findProductShouldThrowError() {

            var productFindByIdRequest = RequestById.newBuilder().setId(3L).build();

            assertThrows(
                    StatusRuntimeException.class,
                    () -> service.findById(productFindByIdRequest),
                    "Product with ID 3 wasn't found."
            );

        }

    }

    @Nested
    class FindAll {

        @Test
        @DisplayName("Should be able to list all products with success")
        public void findAllProduct() {

            var products = service.findAll(EmptyRequest.newBuilder().build());

            assertThat(products).isInstanceOf(ProductResponseList.class);
            assertThat(products.getDataCount()).isEqualTo(2L);
            assertThat(products.getDataList())
                    .extracting("id", "name", "price", "stockQuantity")
                    .contains(
                            tuple(1L, "Product A", 10.99, 10),
                            tuple(2L, "Product B", 10.99, 10)
                    );
        }

    }

    @Nested
    class Delete {

        @Test
        @DisplayName("Should be able to delete a product with success")
        public void deleteProduct() {

            var productFindByIdRequest = RequestById.newBuilder().setId(1).build();

            assertDoesNotThrow(() -> service.deleteById(productFindByIdRequest));

        }

        @Test
        @DisplayName("Should be able to throw exception when product don't exists by id")
        public void deleteProductShouldThrowError() {

            var productFindByIdRequest = RequestById.newBuilder().setId(3L).build();

            assertThrows(
                    StatusRuntimeException.class,
                    () -> service.deleteById(productFindByIdRequest),
                    "Product with ID 3 wasn't found."
            );

        }

    }


}
