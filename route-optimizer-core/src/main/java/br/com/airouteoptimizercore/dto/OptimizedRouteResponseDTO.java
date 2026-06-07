package br.com.airouteoptimizercore.dto;

import java.util.List;

public record OptimizedRouteResponseDTO(
        String requestId,
        List<VehicleRouteResultDTO> routes,
        List<String> unassignedStopIds,
        List<String> idleVehicleIds,
        Double totalCost,
        String summary
) {}