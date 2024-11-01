package br.patrimony.system.controllers;

import br.patrimony.system.dtos.requests.inventory.InventoryRequest;
import br.patrimony.system.dtos.requests.inventory.TransferBuildingRequest;
import br.patrimony.system.services.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("supply")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    @Autowired


    @GetMapping
    public ResponseEntity getAll(){
        return inventoryService.getAllInventory();
    }

    @PostMapping
    public ResponseEntity registerInventory(@RequestBody @Valid InventoryRequest inventoryRequest){
        return inventoryService.registerInventory(inventoryRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity transferBuilding(@PathVariable Long id, @RequestBody TransferBuildingRequest request){
        return inventoryService.transferBuilding(id, request);
    }
}
