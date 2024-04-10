package br.patrimony.system.repositories;

import br.patrimony.system.models.Building;
import br.patrimony.system.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department existsByName(String name);
    boolean existsByNameAndBuilding(String name, Building building);

    //list pq retorna esse metodo retorna uma lista, mesmo que for so um setor
    List<Department> findAllByBuildingId(Long id);

    Department findByName(String name);
    Department findByNameAndBuilding(String departmentName, Building building);

}
