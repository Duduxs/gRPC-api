package com.edudev.grpcspringapi.util;

import com.edudev.grpcspringapi.domain.Product;
import com.edudev.grpcspringapi.dto.ProductInputDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductConverterUtilTest {

    @Test
    @DisplayName("Should be able to convert product to ProductOutputDTO")
    public void toProductOutputDTO() {

        var product = new Product(1L, "Guaraná", 4.0, 5);

        var productOutputDTO = ProductConverterUtil.toProductOutputDTO(product);

        assertThat(product)
                .usingRecursiveComparison()
                .isEqualTo(productOutputDTO);


    }

    @Test
    @DisplayName("Should be able to convert ProductInputDTO to product")
    public void toProduct() {

        var productInputDTO = new ProductInputDTO("Guaraná", 4.0, 5);

        var product = ProductConverterUtil.toProduct(productInputDTO);

        assertThat(productInputDTO)
                .usingRecursiveComparison()
                .isEqualTo(product);


    }

}
