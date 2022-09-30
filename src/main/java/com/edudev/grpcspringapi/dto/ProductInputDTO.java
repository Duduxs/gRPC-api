package com.edudev.grpcspringapi.dto;

public record ProductInputDTO(
        String name,
        Double price,
        Integer quantityInStock
) { }
