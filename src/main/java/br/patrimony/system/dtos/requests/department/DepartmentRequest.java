package br.patrimony.system.dtos.requests.department;

import jakarta.validation.constraints.NotBlank;

public record DepartmentRequest(@NotBlank String name, @NotBlank String buildingName) {
}
