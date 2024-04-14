package br.patrimony.system.dtos.responses.building;

import java.util.List;

public record BuildingDepartmentResponse(Long id, String buildingName, List<String > departmentsName) {
}
