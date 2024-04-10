package br.patrimony.system.dtos.building;

import jakarta.validation.constraints.NotBlank;

public record BuildingRequest(@NotBlank String name) {
}
