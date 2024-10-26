package br.patrimony.system.dtos.responses.raw;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SupplyResponse(@NotNull Long id, @NotBlank String item, @NotNull int quantity, @NotNull Double kilograms,
                             @NotNull Double cost, @NotBlank String status) {
}
