package br.patrimony.system.dtos.patrimony;

import br.patrimony.system.models.Building;
import br.patrimony.system.models.Department;

public record PatrimonyRequest(String object, String building, String department) {
}
