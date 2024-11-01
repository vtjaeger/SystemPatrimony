package br.patrimony.system.services;

import br.patrimony.system.dtos.requests.supply.SupplyRequest;
import br.patrimony.system.dtos.requests.supply.TransferBuildingRequest;
import br.patrimony.system.dtos.responses.supply.SupplyResponse;
import br.patrimony.system.models.Building;
import br.patrimony.system.models.Status;
import br.patrimony.system.models.Supply;
import br.patrimony.system.repositories.BuildingRepository;
import br.patrimony.system.repositories.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplyService {
    @Autowired
    private SupplyRepository supplyRepository;
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

    public ResponseEntity getAllSupplys() {
        List<Supply> supplies = supplyRepository.findAll();
        List<SupplyResponse> response = supplies.stream()
                .map(r -> new SupplyResponse(
                                r.getId(),
                                r.getItem(),
                                r.getQuantity(),
                                r.getKilograms(),
                                r.getBuilding().getName(),
                                r.getCost(),
                                r.getStatus()
                        )
                ).collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity registerSupply(SupplyRequest supplyRequest) {
        if (buildingRepository.findByName(supplyRequest.building()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("building does not exist, contact the admin");
        } else {
            Building building = buildingRepository.findByName(supplyRequest.building());
            Optional<Supply> existingSupply = supplyRepository.findByItemAndBuildingId(supplyRequest.item(), building.getId());

            // valor padrao se estiver fora do hash
            Double unitCost = costPerItem.getOrDefault(supplyRequest.item(), 1.0);
            Double unitKilograms = kilogramsPerItem.getOrDefault(supplyRequest.item(), 1.0);

            if (existingSupply.isPresent()) {
                var supply = existingSupply.get();

                supply.setQuantity(supply.getQuantity() + supplyRequest.quantity());

                double updatedCost = unitCost * supplyRequest.quantity();
                supply.setCost(supply.getCost() + updatedCost);

                double updatedKilograms = unitKilograms * supplyRequest.quantity();
                supply.setKilograms(supply.getKilograms() + updatedKilograms);

                supplyRepository.save(supply);
                return ResponseEntity.ok().body(new SupplyResponse(
                        supply.getId(),
                        supply.getItem(),
                        supply.getQuantity(),
                        supply.getKilograms(),
                        supply.getBuilding().getName(),
                        supply.getCost(),
                        supply.getStatus()));
            }

            Supply newSupply = new Supply();
            newSupply.setItem(supplyRequest.item());
            newSupply.setQuantity(supplyRequest.quantity());
            newSupply.setBuilding(building);
            newSupply.setCost(unitCost * supplyRequest.quantity());
            newSupply.setKilograms(unitKilograms * supplyRequest.quantity());
            newSupply.setStatus(Status.AVAILABLE);

            Supply savedSupply = supplyRepository.save(newSupply);

            return ResponseEntity.ok(new SupplyResponse(
                    savedSupply.getId(),
                    savedSupply.getItem(),
                    savedSupply.getQuantity(),
                    savedSupply.getKilograms(),
                    savedSupply.getBuilding().getName(),
                    savedSupply.getCost(),
                    savedSupply.getStatus()
            ));
        }
    }

    public ResponseEntity<SupplyResponse> transferBuilding(Long id, TransferBuildingRequest request) {
        Optional<Supply> supplyOptional = supplyRepository.findById(id);
        Optional<Building> buildingOptional = buildingRepository.findById(request.to());

        if (supplyOptional.isEmpty() || buildingOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Supply supply = supplyOptional.get();
        Building newBuilding = buildingOptional.get();

        if (request.to().equals(supply.getBuilding().getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (request.quantity() <= 0 || request.quantity() > supply.getQuantity()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (request.quantity() == supply.getQuantity()) {
            supply.setBuilding(newBuilding);
            supplyRepository.save(supply);

            return ResponseEntity.ok(new SupplyResponse(
                    supply.getId(),
                    supply.getItem(),
                    supply.getQuantity(),
                    supply.getKilograms(),
                    newBuilding.getName(),
                    supply.getCost(),
                    supply.getStatus()
            ));
        }

        if (supply.getQuantity() <= 0) {
            supply.setStatus(Status.MISSING);
        }

        supply.setQuantity(supply.getQuantity() - request.quantity());

        Double cosItem = costPerItem.getOrDefault(supply.getItem(), 0.0);
        Double kilogramsItem = kilogramsPerItem.getOrDefault(supply.getItem(), 0.0);

        double costSupply = cosItem * supply.getQuantity();
        double kilogramSupply = kilogramsItem * supply.getQuantity();
        supply.setCost(costSupply);
        supply.setKilograms(kilogramSupply);
        supplyRepository.save(supply);

        double costNewSupply = cosItem * request.quantity();
        double kilogramsNewSupply = kilogramsItem * request.quantity();

        Optional<Supply> existingsSupplyOptional = supplyRepository.findByItemAndBuildingId(supply.getItem(), newBuilding.getId());

        if (existingsSupplyOptional.isPresent()) {
            Supply existingSupply = existingsSupplyOptional.get();
            existingSupply.setQuantity(existingSupply.getQuantity() + request.quantity());
            existingSupply.setCost(existingSupply.getCost() + costNewSupply);
            existingSupply.setKilograms(existingSupply.getKilograms() + kilogramsNewSupply);
            supplyRepository.save(existingSupply);

            return ResponseEntity.ok(new SupplyResponse(
                    existingSupply.getId(),
                    existingSupply.getItem(),
                    existingSupply.getQuantity(),
                    existingSupply.getKilograms(),
                    existingSupply.getBuilding().getName(),
                    existingSupply.getCost(),
                    existingSupply.getStatus()));
        } else {
            Supply newSupply = new Supply();
            newSupply.setItem(supply.getItem());
            newSupply.setQuantity(request.quantity());
            newSupply.setCost(costNewSupply);
            newSupply.setKilograms(kilogramsNewSupply);
            newSupply.setBuilding(newBuilding);
            newSupply.setStatus(supply.getStatus());

            supplyRepository.save(newSupply);

            return ResponseEntity.ok(new SupplyResponse(
                    newSupply.getId(),
                    newSupply.getItem(),
                    newSupply.getQuantity(),
                    newSupply.getKilograms(),
                    newSupply.getBuilding().getName(),
                    newSupply.getCost(),
                    newSupply.getStatus()));
        }
    }
}
