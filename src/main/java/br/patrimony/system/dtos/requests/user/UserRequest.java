package br.patrimony.system.dtos.requests.user;

public record UserRequest(String login, String password, String role) {
}
