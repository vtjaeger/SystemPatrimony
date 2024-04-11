package br.patrimony.system.dtos.responses.building;

import br.patrimony.system.models.Department;

import java.util.List;

public record BuildingDepartmentResponse(Long id, String buildingName, List<String > departmentsName) {
}
