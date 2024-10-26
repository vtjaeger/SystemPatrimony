package br.patrimony.system.dtos.requests.raw;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SupplyRequest(@NotBlank String item, @NotNull int quantity, @NotNull Double kilograms, @NotNull Double cost,
                            @NotBlank String status) {
}
