package br.patrimony.system.controllers;

import br.patrimony.system.dtos.requests.supply.SupplyRequest;
import br.patrimony.system.dtos.requests.supply.TransferBuildingRequest;
import br.patrimony.system.services.SupplyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("supply")
public class SupplyController {
    @Autowired
    private SupplyService supplyService;
    @Autowired


    @GetMapping
    public ResponseEntity getAll(){
        return supplyService.getAllSupplys();
    }

    @PostMapping
    public ResponseEntity registerSupply(@RequestBody @Valid SupplyRequest supplyRequest){
        return supplyService.registerSupply(supplyRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity transferBuilding(@PathVariable Long id, @RequestBody TransferBuildingRequest request){
        return supplyService.transferBuilding(id, request);
    }
}
