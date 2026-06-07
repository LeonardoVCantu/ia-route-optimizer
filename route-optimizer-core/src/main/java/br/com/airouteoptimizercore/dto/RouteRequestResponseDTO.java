package br.com.airouteoptimizercore.dto;

import java.time.LocalDateTime;

public record RouteRequestResponseDTO(
        String requestId,
        String internalId,
        String message,
        LocalDateTime timestamp
) {}