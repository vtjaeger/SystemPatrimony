package br.patrimony.system.dtos.department;

import br.patrimony.system.models.Building;
import jakarta.validation.constraints.NotBlank;

public record DepartmentRequest(@NotBlank String name, @NotBlank String buildingName) {
}
