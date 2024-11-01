package br.patrimony.system.dtos.requests.inventory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InventoryRequest(@NotBlank String item, @NotNull int quantity, Double cost, @NotBlank String building) {
}
