package br.patrimony.system.dtos.requests.inventory;

import jakarta.validation.constraints.NotNull;

public record RemoveQuantity(@NotNull int quantity) {
}
