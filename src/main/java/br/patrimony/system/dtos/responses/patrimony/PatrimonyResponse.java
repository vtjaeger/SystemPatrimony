package br.patrimony.system.dtos.responses.patrimony;

import br.patrimony.system.models.Category;

public record PatrimonyResponse(Long id, String object, Category category, String building, String department,
                                String responsible) {
}
