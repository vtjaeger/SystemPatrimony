package br.patrimony.system.dtos.requests.user;

import br.patrimony.system.models.UserRole;

public record UserRequest(String login, String password, UserRole role) {
}
