package br.patrimony.system.dtos.responses.department;

import br.patrimony.system.dtos.responses.patrimony.PatrimonyIdNameResponse;

import java.util.List;

public record DepartmentResponse(Long buildingId, String buildingName, String departmentName,
                                 List<PatrimonyIdNameResponse> patrimonies) {
}
