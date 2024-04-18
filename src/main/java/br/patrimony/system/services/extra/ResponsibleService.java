package br.patrimony.system.services.extra;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ResponsibleService {
    private final HashMap<String, String> departmentRoles = new HashMap<>();

    public ResponsibleService() {
        departmentRoles.put("CQ", "Inspetor CQ");
        departmentRoles.put("GQ", "Coordenador GQ");
        departmentRoles.put("TI", "Tecnico TI");
        departmentRoles.put("PRODUCAO", "Lider Producao");
        departmentRoles.put("LOG", "Coordenador Logistica");
        departmentRoles.put("EXP", "Gestor Expedicao");
    }

    public String determinateResponsible(String departmentName){
        //se o setor tiver no hash acima, se nao direcao
        return departmentRoles.getOrDefault(departmentName, "Direcao");
    }
}
