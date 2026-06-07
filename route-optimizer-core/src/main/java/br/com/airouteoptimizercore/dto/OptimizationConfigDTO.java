package br.com.airouteoptimizercore.dto;

import br.com.airouteoptimizercore.enums.OptimizationType;
import jakarta.validation.constraints.NotNull;

public record OptimizationConfigDTO(
        @NotNull(message = "O critério de otimização (optimizeBy) é obrigatório")
        OptimizationType optimizeBy
) {}
