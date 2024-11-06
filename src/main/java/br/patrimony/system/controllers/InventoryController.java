package br.patrimony.system.controllers;

import br.patrimony.system.dtos.requests.inventory.AddQuantity;
import br.patrimony.system.dtos.requests.inventory.InventoryRequest;
import br.patrimony.system.dtos.requests.inventory.RemoveQuantity;
import br.patrimony.system.dtos.requests.inventory.TransferBuildingRequest;
import br.patrimony.system.services.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

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

    @PatchMapping("/{id}/remove")
    public ResponseEntity removeQuantity(@PathVariable Long id, @RequestBody RemoveQuantity dto){
        return inventoryService.removeQuantity(id, dto);
    }

    @PatchMapping("/{id}/add")
    public ResponseEntity addQuantity(@PathVariable Long id, @RequestBody AddQuantity dto){
        return inventoryService.addQuantity(id, dto);
    }
}
