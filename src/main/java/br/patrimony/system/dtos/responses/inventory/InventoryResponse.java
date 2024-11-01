package br.patrimony.system.dtos.responses.inventory;

import br.patrimony.system.models.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InventoryResponse(@NotNull Long id, @NotBlank String item, @NotNull int quantity, @NotNull Double kilograms,
                                @NotBlank String building, @NotNull Double cost, @NotNull Status status) {
}
