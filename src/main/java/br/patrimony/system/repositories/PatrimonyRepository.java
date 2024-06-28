package br.patrimony.system.repositories;

import br.patrimony.system.models.Building;
import br.patrimony.system.models.Category;
import br.patrimony.system.models.Department;
import br.patrimony.system.models.Patrimony;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatrimonyRepository extends JpaRepository<Patrimony, Long> {
    List<Patrimony> findAllByBuildingAndDepartment(Building building, Department department);
    Patrimony findByObject(String name);
    List<Patrimony> findAllByCategory(Category category);
    @Query("select p from Patrimony p where p.acquisitionDate < :date")
    List<Patrimony> findAllAcquiredBefore(@Param("date") LocalDate date);
}
