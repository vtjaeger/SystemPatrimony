package br.patrimony.system.dtos.requests.supply;

import br.patrimony.system.models.Building;
import br.patrimony.system.models.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SupplyRequest(@NotBlank String item, @NotNull int quantity, Double cost,
                            @NotBlank String building) {
}
