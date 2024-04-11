package br.patrimony.system.dtos.requests.building;

import jakarta.validation.constraints.NotBlank;

public record BuildingRequest(@NotBlank String name) {
}
