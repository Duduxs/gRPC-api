package com.edudev.grpcspringapi.services.impl;

import com.edudev.grpcspringapi.domain.Product;
import com.edudev.grpcspringapi.dto.ProductInputDTO;
import com.edudev.grpcspringapi.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        Assertions.assertThat(product)
                .usingRecursiveComparison()
                .isEqualTo(output);

    }

}
