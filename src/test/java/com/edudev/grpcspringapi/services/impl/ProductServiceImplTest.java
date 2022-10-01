package com.edudev.grpcspringapi.services.impl;

import com.edudev.grpcspringapi.domain.Product;
import com.edudev.grpcspringapi.dto.ProductInputDTO;
import com.edudev.grpcspringapi.exception.ProductAlreadyExistsException;
import com.edudev.grpcspringapi.exception.ProductNotFoundException;
import com.edudev.grpcspringapi.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl service;

    @Nested
    class Create {

        @Test
        @DisplayName("Should be able to create a product with success")
        public void create() {

            var product = new Product(1L, "Product", 1.0, 10);

            var input = new ProductInputDTO("Entry", 5.0, 10);

            when(repository.save(any()))
                    .thenReturn(product);

            var output = service.create(input);

            verify(repository).save(any());

            assertThat(product)
                    .usingRecursiveComparison()
                    .isEqualTo(output);

        }

        @Test
        @DisplayName("Should not be able to create a product when already exists a product with same name")
        public void shouldNotCreateWhenExistsConflict() {

            var product = new Product(1L, "Product", 1.0, 10);

            var input = new ProductInputDTO("Product", 5.0, 10);

            when(repository.findByNameIgnoreCase(any()))
                    .thenReturn(Optional.of(product));

            assertThatExceptionOfType(ProductAlreadyExistsException.class)
                    .isThrownBy(() -> service.create(input))
                    .withMessage("Product Product already exists in the system.");

            verify(repository).findByNameIgnoreCase(any());
            verify(repository, never()).save(any());

        }
    }

    @Nested
    class FindById {

        @Test
        @DisplayName("Should be able to return a product by its id")
        public void findById() {

            var product = new Product(1L, "Product", 1.0, 10);

            when(repository.findById(any()))
                    .thenReturn(Optional.of(product));

            var output = service.findById(1L);

            verify(repository).findById(any());

            assertThat(product)
                    .usingRecursiveComparison()
                    .isEqualTo(output);

        }

        @Test
        @DisplayName("Should be able to throw Product not found when id doesn't exists")
        public void findByIdShouldThrow() {

            when(repository.findById(any()))
                    .thenReturn(Optional.empty());


            assertThrows(
                    ProductNotFoundException.class,
                    () -> service.findById(1L),
                    "Product with ID 1 wasn't found."
            );

            verify(repository).findById(any());

        }


    }

    @Nested
    class Delete {

        @Test
        @DisplayName("Should be able to delete a product by its id")
        public void delete() {

            var product = new Product(1L, "Product", 1.0, 10);

            when(repository.findById(any()))
                    .thenReturn(Optional.of(product));

            Assertions.assertThatNoException().isThrownBy(() ->service.deleteBy(1L));

            verify(repository).findById(any());
            verify(repository).delete(any());

        }

        @Test
        @DisplayName("Should be able to throw Product not found when id doesn't exists in delete")
        public void deleteByIdShouldThrow() {

            when(repository.findById(any()))
                    .thenReturn(Optional.empty());


            assertThrows(
                    ProductNotFoundException.class,
                    () -> service.deleteBy(1L),
                    "Product with ID 1 wasn't found."
            );

            verify(repository).findById(any());

        }

    }
}
