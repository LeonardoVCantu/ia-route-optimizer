package br.com.airouteoptimizercore.dto;

import br.com.airouteoptimizercore.enums.StopType;
import br.com.airouteoptimizercore.enums.UrgencyLevel;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalTime;

public record StopDTO(
        @NotBlank(message = "O ID da parada é obrigatório")
        String id,

        @NotBlank(message = "A descrição da parada é obrigatória")
        String description,

        @NotBlank(message = "O endereço da parada é obrigatório")
        String address,

        @NotNull(message = "O volume da carga é obrigatório")
        @PositiveOrZero(message = "O volume da carga não pode ser negativo")
        Double volumeM3,

        @NotNull(message = "O peso da carga é obrigatório")
        @PositiveOrZero(message = "O peso da carga não pode ser negativo")
        Double weightKg,

        @NotNull(message = "O tipo de parada (PICKUP/DELIVERY) é obrigatório")
        StopType type,

        @NotNull(message = "A prioridade é obrigatória")
        UrgencyLevel priority,

        @NotNull(message = "O tempo de serviço estimado é obrigatório")
        @Positive(message = "O tempo de serviço deve ser maior que zero")
        Integer estimatedServiceTimeMinutes,

        @NotNull(message = "O horário inicial de disponibilidade é obrigatório")
        @JsonFormat(pattern = "HH:mm") LocalTime availableFrom,

        @NotNull(message = "O horário final de disponibilidade é obrigatório")
        @JsonFormat(pattern = "HH:mm") LocalTime availableUntil

) {
}
