package com.edudev.grpcspringapi.services.impl;

import com.edudev.grpcspringapi.domain.Product;
import com.edudev.grpcspringapi.dto.ProductInputDTO;
import com.edudev.grpcspringapi.exception.ProductAlreadyExistsException;
import com.edudev.grpcspringapi.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl service;

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
