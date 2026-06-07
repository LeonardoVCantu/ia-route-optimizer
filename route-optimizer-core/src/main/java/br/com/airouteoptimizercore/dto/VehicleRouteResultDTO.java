package br.com.airouteoptimizercore.dto;

import java.util.List;

public record VehicleRouteResultDTO(
        String vehicleId,
        List<RouteStepDTO> steps,
        Double usedWeight,
        Double usedVolume,
        String routeStartTime,
        String routeEndTime
) {
}
