package com.edudev.grpcspringapi.util;

import com.edudev.grpcspringapi.domain.Product;
import com.edudev.grpcspringapi.dto.ProductInputDTO;
import com.edudev.grpcspringapi.dto.ProductOutputDTO;

public class ProductConverterUtil {

    public static ProductOutputDTO toProductOutputDTO(Product p) {
        return new ProductOutputDTO(p.getId(), p.getName(), p.getPrice(), p.getQuantityInStock());
    }

    public static Product toProduct(ProductInputDTO p) {
        return new Product(null, p.name(), p.price(), p.quantityInStock());
    }

}
