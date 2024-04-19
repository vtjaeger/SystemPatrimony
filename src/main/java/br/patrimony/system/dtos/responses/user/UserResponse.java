package br.patrimony.system.dtos.responses.user;

import br.patrimony.system.models.UserRole;

public record UserResponse(Long id, String login, UserRole role) {
}
