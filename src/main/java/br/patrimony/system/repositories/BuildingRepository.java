package br.patrimony.system.repositories;

import br.patrimony.system.models.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
    boolean existsByName(String name);
    Building findByName(String name);
}
