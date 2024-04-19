package br.patrimony.system.models;

public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    ASSISTANT("assistant"),
    ANALYST("analyst"),
    SPECIALIST("specialist"),
    COORDINATOR("coordinator"),
    MANAGER("manager");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
