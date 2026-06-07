package br.com.airouteoptimizercore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RouteRequestDTO(
        @NotBlank(message = "O ID da requisição é obrigatório")
        String requestId,

        @NotEmpty(message = "A lista de veículos não pode estar vazia")
        @Valid List<VehicleDTO> vehicles,

        @NotEmpty(message = "A lista de paradas (stops) não pode estar vazia")
        @Valid List<StopDTO> stops,

        @NotNull(message = "As configurações de otimização são obrigatórias")
        @Valid OptimizationConfigDTO config
) {
}
