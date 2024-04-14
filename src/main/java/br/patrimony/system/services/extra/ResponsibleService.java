package br.patrimony.system.services.extra;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ResponsibleService {
    private final HashMap<String, String> departmentRoles = new HashMap<>();

    public ResponsibleService() {
        departmentRoles.put("cq", "Assistente");
        departmentRoles.put("gq", "Gestor");
        departmentRoles.put("ti", "Direcao");
        departmentRoles.put("expedicao", "teste");
    }

    public String determinateResponsible(String departmentName){
        if(departmentRoles.containsKey(departmentName)){
            return departmentRoles.get(departmentName);
        }
        else {
            return "Erro";
        }
    }
}
