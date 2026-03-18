package com.example.sportradarbe.exception;

public record ApiValidationError(
        String field,
        String message
) {}
