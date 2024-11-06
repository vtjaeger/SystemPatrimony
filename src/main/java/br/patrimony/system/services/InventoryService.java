package br.patrimony.system.services;

import br.patrimony.system.dtos.requests.inventory.AddQuantity;
import br.patrimony.system.dtos.requests.inventory.InventoryRequest;
import br.patrimony.system.dtos.requests.inventory.RemoveQuantity;
import br.patrimony.system.dtos.requests.inventory.TransferBuildingRequest;
import br.patrimony.system.dtos.responses.inventory.InventoryResponse;
import br.patrimony.system.models.Building;
import br.patrimony.system.models.Status;
import br.patrimony.system.models.Inventory;
import br.patrimony.system.repositories.BuildingRepository;
import br.patrimony.system.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    private final HashMap<String, Double> costPerItem = new HashMap<>() {{
        put("teste", 10.0);
        put("essencia", 2.5);
        put("embalagem", 1.2);
    }};
    private final HashMap<String, Double> kilogramsPerItem = new HashMap<>() {{
        put("teste", 2.0);
        put("essencia", 1.1);
        put("embalagem", 0.5);
    }};

    public ResponseEntity getAllInventory() {
        List<Inventory> inventories = inventoryRepository.findAll();
        List<InventoryResponse> response = inventories.stream()
                .map(i -> new InventoryResponse(
                                i.getId(),
                                i.getItem(),
                                i.getQuantity(),
                                i.getKilograms(),
                                i.getBuilding().getName(),
                                i.getCost(),
                                i.getStatus()
                        )
                ).collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity registerInventory(InventoryRequest inventoryRequest) {
        if (buildingRepository.findByName(inventoryRequest.building()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("building does not exist, contact the admin");
        } else {
            Building building = buildingRepository.findByName(inventoryRequest.building());
            Optional<Inventory> existingInventory = inventoryRepository.findByItemAndBuildingId(inventoryRequest.item(),
                    building.getId());

            // valor padrao se estiver fora do hash
            Double unitCost = costPerItem.getOrDefault(inventoryRequest.item(), 1.0);
            Double unitKilograms = kilogramsPerItem.getOrDefault(inventoryRequest.item(), 1.0);

            if (existingInventory.isPresent()) {
                var inventory = existingInventory.get();

                inventory.setQuantity(inventory.getQuantity() + inventoryRequest.quantity());

                double updatedCost = unitCost * inventoryRequest.quantity();
                inventory.setCost(inventory.getCost() + updatedCost);

                double updatedKilograms = unitKilograms * inventoryRequest.quantity();
                inventory.setKilograms(inventory.getKilograms() + updatedKilograms);

                inventoryRepository.save(inventory);
                return ResponseEntity.ok().body(new InventoryResponse(
                        inventory.getId(),
                        inventory.getItem(),
                        inventory.getQuantity(),
                        inventory.getKilograms(),
                        inventory.getBuilding().getName(),
                        inventory.getCost(),
                        inventory.getStatus()));
            }

            Inventory newInventory = new Inventory();
            newInventory.setItem(inventoryRequest.item());
            newInventory.setQuantity(inventoryRequest.quantity());
            newInventory.setBuilding(building);
            newInventory.setCost(unitCost * inventoryRequest.quantity());
            newInventory.setKilograms(unitKilograms * inventoryRequest.quantity());
            newInventory.setStatus(Status.AVAILABLE);

            Inventory savedInventory = inventoryRepository.save(newInventory);

            return ResponseEntity.ok(new InventoryResponse(
                    savedInventory.getId(),
                    savedInventory.getItem(),
                    savedInventory.getQuantity(),
                    savedInventory.getKilograms(),
                    savedInventory.getBuilding().getName(),
                    savedInventory.getCost(),
                    savedInventory.getStatus()
            ));
        }
    }

    public ResponseEntity<InventoryResponse> transferBuilding(Long id, TransferBuildingRequest request) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findById(id);
        Optional<Building> buildingOptional = buildingRepository.findById(request.to());

        if (inventoryOptional.isEmpty() || buildingOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Inventory inventory = inventoryOptional.get();
        Building newBuilding = buildingOptional.get();

        if (request.to().equals(inventory.getBuilding().getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (request.quantity() <= 0 || request.quantity() > inventory.getQuantity()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (request.quantity() == inventory.getQuantity()) {
            inventory.setBuilding(newBuilding);
            inventoryRepository.save(inventory);

            return ResponseEntity.ok(new InventoryResponse(
                    inventory.getId(),
                    inventory.getItem(),
                    inventory.getQuantity(),
                    inventory.getKilograms(),
                    newBuilding.getName(),
                    inventory.getCost(),
                    inventory.getStatus()
            ));
        }

        if (inventory.getQuantity() <= 0) {
            inventory.setStatus(Status.MISSING);
        }

        inventory.setQuantity(inventory.getQuantity() - request.quantity());

        Double cost = costPerItem.getOrDefault(inventory.getItem(), 0.0);
        Double kilogramsItem = kilogramsPerItem.getOrDefault(inventory.getItem(), 0.0);

        double costSupply = cost * inventory.getQuantity();
        double kilogramSupply = kilogramsItem * inventory.getQuantity();
        inventory.setCost(costSupply);
        inventory.setKilograms(kilogramSupply);
        inventoryRepository.save(inventory);

        double costNewSupply = cost * request.quantity();
        double kilogramsNewSupply = kilogramsItem * request.quantity();

        Optional<Inventory> existingsSupplyOptional = inventoryRepository.findByItemAndBuildingId(inventory.getItem(), newBuilding.getId());

        if (existingsSupplyOptional.isPresent()) {
            Inventory existingSupply = existingsSupplyOptional.get();
            existingSupply.setQuantity(existingSupply.getQuantity() + request.quantity());
            existingSupply.setCost(existingSupply.getCost() + costNewSupply);
            existingSupply.setKilograms(existingSupply.getKilograms() + kilogramsNewSupply);
            inventoryRepository.save(existingSupply);

            return ResponseEntity.ok(new InventoryResponse(
                    existingSupply.getId(),
                    existingSupply.getItem(),
                    existingSupply.getQuantity(),
                    existingSupply.getKilograms(),
                    existingSupply.getBuilding().getName(),
                    existingSupply.getCost(),
                    existingSupply.getStatus()));
        } else {
            Inventory newSupply = new Inventory();
            newSupply.setItem(inventory.getItem());
            newSupply.setQuantity(request.quantity());
            newSupply.setCost(costNewSupply);
            newSupply.setKilograms(kilogramsNewSupply);
            newSupply.setBuilding(newBuilding);
            newSupply.setStatus(inventory.getStatus());

            inventoryRepository.save(newSupply);

            return ResponseEntity.ok(new InventoryResponse(
                    newSupply.getId(),
                    newSupply.getItem(),
                    newSupply.getQuantity(),
                    newSupply.getKilograms(),
                    newSupply.getBuilding().getName(),
                    newSupply.getCost(),
                    newSupply.getStatus()));
        }
    }

    public ResponseEntity removeQuantity(Long id, RemoveQuantity dto) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findById(id);
        if (inventoryOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        var item = inventoryOptional.get();
        if (dto.quantity() > item.getQuantity()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Double costPerUnit = costPerItem.getOrDefault(item.getItem(), 0.0);
        Double kilogramsPerUnit = kilogramsPerItem.getOrDefault(item.getItem(), 0.0);
        item.setQuantity(item.getQuantity() - dto.quantity());

        Double reducedCost = costPerUnit * dto.quantity();
        item.setCost(item.getCost() - reducedCost);

        Double reducedKilograms = kilogramsPerUnit * dto.quantity();
        item.setKilograms(item.getKilograms() - reducedKilograms);

        inventoryRepository.save(item);

        return ResponseEntity.ok().body(new InventoryResponse(
                item.getId(),
                item.getItem(),
                item.getQuantity(),
                item.getKilograms(),
                item.getBuilding().getName(),
                item.getCost(),
                item.getStatus()
        ));
    }

    public ResponseEntity addQuantity(Long id, AddQuantity dto){
        Optional<Inventory> inventoryOptional = inventoryRepository.findById(id);
        if (inventoryOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        var item = inventoryOptional.get();
        if (dto.quantity() > item.getQuantity()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Double costPerUnit = costPerItem.getOrDefault(item.getItem(), 0.0);
        Double kilogramsPerUnit = kilogramsPerItem.getOrDefault(item.getItem(), 0.0);

        item.setQuantity(item.getQuantity() + dto.quantity());

        Double updatedCost = costPerUnit * dto.quantity();
        item.setCost(item.getCost() + updatedCost);

        Double updatedKilograms = kilogramsPerUnit * dto.quantity();
        item.setKilograms(item.getKilograms() + updatedKilograms);

        inventoryRepository.save(item);

        return ResponseEntity.ok().body(new InventoryResponse(
                item.getId(),
                item.getItem(),
                item.getQuantity(),
                item.getKilograms(),
                item.getBuilding().getName(),
                item.getCost(),
                item.getStatus()
        ));
    }
}
