package br.com.airouteoptimizercore.dto;

import br.com.airouteoptimizercore.enums.StopType;

public record RouteStepDTO(
        String stopId,
        Integer sequence,
        String estimatedArrivalTime,
        StopType type
) {

}
