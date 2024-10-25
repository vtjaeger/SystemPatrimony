package br.patrimony.system.dtos.responses.raw;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RawResponse(@NotNull Long id, @NotNull Double quantity, @NotNull String kilograms, @NotNull Double cost,
                          @NotBlank String status) {
}
