package br.patrimony.system.repositories;

import br.patrimony.system.models.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    Supply findByItem(String item);
    Optional<Supply> findByItemAndBuildingId(String item, Long buildingId);
}
