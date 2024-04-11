package br.patrimony.system.controllers;

import br.patrimony.system.dtos.requests.building.BuildingRequest;
import br.patrimony.system.services.BuildingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("building")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;

    @PostMapping
    public ResponseEntity registerBuilding(@RequestBody @Valid BuildingRequest buildingRequest){
        return buildingService.registerBuilding(buildingRequest);
    }

    @GetMapping
    public ResponseEntity getAllBuildings(){
        return buildingService.getAllBuildings();
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneBuilding(@PathVariable(value = "id") Long id){
        return buildingService.getOneBuilding(id);
    }

}
