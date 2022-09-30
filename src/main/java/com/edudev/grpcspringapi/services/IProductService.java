package com.edudev.grpcspringapi.services;

import com.edudev.grpcspringapi.dto.ProductInputDTO;
import com.edudev.grpcspringapi.dto.ProductOutputDTO;

import java.util.Collection;

public interface IProductService {

    ProductOutputDTO findById(Long id);
    Collection<ProductOutputDTO> findAll();
    ProductOutputDTO create(ProductInputDTO input);
    void deleteBy(Long id);

}
