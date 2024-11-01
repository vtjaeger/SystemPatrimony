package br.patrimony.system.repositories;

import br.patrimony.system.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByItem(String item);
    Optional<Inventory> findByItemAndBuildingId(String item, Long buildingId);
}
