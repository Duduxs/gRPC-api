package com.edudev.grpcspringapi.dto;

public record ProductOutputDTO(
        Long id,
        String name,
        Double price,
        Integer quantityInStock
) { }
