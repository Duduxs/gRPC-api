package com.edudev.grpcspringapi.services.impl;

import com.edudev.grpcspringapi.dto.ProductInputDTO;
import com.edudev.grpcspringapi.dto.ProductOutputDTO;
import com.edudev.grpcspringapi.repository.ProductRepository;
import com.edudev.grpcspringapi.services.IProductService;
import com.edudev.grpcspringapi.util.ProductConverterUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductOutputDTO findById(Long id) {
        return null;
    }

    @Override
    public Collection<ProductOutputDTO> findAll() {
        return repository.findAll().stream().map(ProductConverterUtil::toProductOutputDTO).collect(Collectors.toList());
    }

    @Override
    public ProductOutputDTO create(ProductInputDTO input) {

        var product = repository.save(ProductConverterUtil.toProduct(input));

        return ProductConverterUtil.toProductOutputDTO(product);

    }

    @Override
    public void deleteBy(Long id) {
        repository.deleteById(id);
    }
}
