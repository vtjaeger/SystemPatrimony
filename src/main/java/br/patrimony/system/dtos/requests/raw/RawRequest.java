package br.patrimony.system.dtos.requests.raw;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RawRequest(@NotNull Double quantity, @NotBlank String kilograms, @NotNull Double cost, @NotBlank String status) {
}
