package br.patrimony.system.services.extra;

import br.patrimony.system.models.UserRole;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ResponsibleService {
    private final HashMap<String, UserRole> departmentRoles = new HashMap<>();

    public ResponsibleService() {
        departmentRoles.put("CQ", UserRole.INSPETOR_CQ);
        departmentRoles.put("GQ", UserRole.COORDENADOR_GQ);
        departmentRoles.put("TI", UserRole.TECNICO_TI);
        departmentRoles.put("PRODUCAO", UserRole.LIDER_PRODUCAO);
        departmentRoles.put("LOG", UserRole.COORDENADOR_LOGISTICA);
        departmentRoles.put("EXP", UserRole.GESTOR_EXPEDICAO);
    }

    public UserRole determinateResponsible(String departmentName){
        return departmentRoles.getOrDefault(departmentName, UserRole.DIRECAO);
    }
}
