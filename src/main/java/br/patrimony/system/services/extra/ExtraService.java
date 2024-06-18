package br.patrimony.system.services.extra;

import br.patrimony.system.models.Category;
import br.patrimony.system.models.UserRole;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;

@Service
public class ExtraService {
    private final HashMap<String, UserRole> departmentRoles = new HashMap<>();
    private final HashMap<String, Category> categories = new HashMap<>();

    public ExtraService() {
        departmentRoles.put("CQ", UserRole.INSPETOR_CQ);
        departmentRoles.put("GQ", UserRole.COORDENADOR_GQ);
        departmentRoles.put("TI", UserRole.TECNICO_TI);
        departmentRoles.put("PRODUCAO", UserRole.LIDER_PRODUCAO);
        departmentRoles.put("LOG", UserRole.COORDENADOR_LOGISTICA);
        departmentRoles.put("EXP", UserRole.GESTOR_EXPEDICAO);
        //
        categories.put("balanca", Category.MACHINERY);
        categories.put("papel", Category.OFFICE_SUPPLIES);
        categories.put("computador", Category.ELECTRONICS);
        categories.put("impressora", Category.ELECTRONICS);
        categories.put("mesa", Category.FURNITURE);
        categories.put("cadeira", Category.FURNITURE);
        categories.put("envasadora", Category.MACHINERY);
        categories.put("maquina", Category.MACHINERY);
    }

    public UserRole determinateResponsible(String departmentName){
        return departmentRoles.getOrDefault(departmentName, UserRole.DIRECAO);
    }

    public Category determinateCategory(String objectName) {
        return categories.getOrDefault(objectName.toLowerCase(), Category.OTHER);
    }

    public LocalDate determinateRevisionDate(LocalDate acquisitionDate){
        return acquisitionDate.plusYears(1);
    }
}
