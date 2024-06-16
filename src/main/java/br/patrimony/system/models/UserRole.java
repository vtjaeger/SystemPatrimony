package br.patrimony.system.models;

public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    ASSISTANT("assistant"),
    ANALYST("analyst"),
    SPECIALIST("specialist"),
    COORDINATOR("coordinator"),
    MANAGER("manager"),
    INSPETOR_CQ("Inspetor CQ"),
    COORDENADOR_GQ("Coordenador GQ"),
    TECNICO_TI("Tecnico TI"),
    LIDER_PRODUCAO("Lider Producao"),
    COORDENADOR_LOGISTICA("Coordenador Logistica"),
    GESTOR_EXPEDICAO("Gestor Expedicao"),
    DIRECAO("Direcao");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}