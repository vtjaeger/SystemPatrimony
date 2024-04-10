package br.patrimony.system.repositories;

import br.patrimony.system.models.Building;
import br.patrimony.system.models.Department;
import br.patrimony.system.models.Patrimony;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatrimonyRepository extends JpaRepository<Patrimony, Long> {
    List<Patrimony> findAllByBuildingAndDepartment(Building building, Department department);
    Patrimony findByObject(String name);
}
