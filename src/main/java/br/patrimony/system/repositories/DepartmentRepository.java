package br.patrimony.system.repositories;

import br.patrimony.system.models.Building;
import br.patrimony.system.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByNameAndBuilding(String name, Building building);
    
    //lista pq existem departamentos com nomes iguais
    List<Department> findAllByName(String name);

}
