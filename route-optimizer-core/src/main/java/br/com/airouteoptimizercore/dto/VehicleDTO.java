package br.com.airouteoptimizercore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalTime;

public record VehicleDTO(
        @NotBlank(message = "O ID do veículo é obrigatório")
        String id,

        @NotBlank(message = "O modelo do veículo é obrigatório")
        String model,

        @NotBlank(message = "A placa do veículo é obrigatória")
        String licensePlate,

        @NotNull(message = "O volume máximo (m3) é obrigatório")
        @Positive(message = "O volume máximo deve ser um valor positivo")
        Double maxVolumeM3,

        @NotNull(message = "O peso máximo (kg) é obrigatório")
        @Positive(message = "O peso máximo deve ser um valor positivo")
        Double maxWeightKg,

        @NotNull(message = "O consumo de combustível (Km/L) é obrigatório")
        @Positive(message = "O consumo deve ser um valor positivo")
        Double fuelConsumptionKmL,

        @NotNull(message = "O custo do litro (Km/L) é obrigatório")
        @Positive(message = "O custo deve ser um valor positivo")
        BigDecimal costPerKm,

        @NotBlank(message = "O endereço atual do veículo é obrigatório")
        String currentAddress,

        @NotNull(message = "O início do turno é obrigatório")
        @JsonFormat(pattern = "HH:mm") LocalTime shiftStart,

        @NotNull(message = "O início do intervalo é obrigatório")
        @JsonFormat(pattern = "HH:mm") LocalTime breakStart,

        @NotNull(message = "O fim do intervalo é obrigatório")
        @JsonFormat(pattern = "HH:mm") LocalTime breakEnd,

        @NotNull(message = "O fim do turno é obrigatório")
        @JsonFormat(pattern = "HH:mm") LocalTime shiftEnd
) {
}
