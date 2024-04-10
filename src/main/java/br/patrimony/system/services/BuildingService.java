package br.patrimony.system.services;

import br.patrimony.system.dtos.building.BuildingRequest;
import br.patrimony.system.models.Building;
import br.patrimony.system.repositories.BuildingRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;
    public ResponseEntity registerBuilding(@RequestBody @Valid BuildingRequest buildingRequest){
        if(buildingRepository.existsByName(buildingRequest.name())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Building already registered");
        }
        var newBuilding = new Building(buildingRequest);
        return ResponseEntity.ok().body(buildingRepository.save(newBuilding));
    }

    public ResponseEntity getAllBuildings(){
        List<Building> buildingList = buildingRepository.findAll();
        return ResponseEntity.ok().body(buildingList);
    }

    public ResponseEntity getOneBuilding(@PathVariable(value = "id") Long id){
        var building = buildingRepository.findById(id);
        if(!building.isEmpty()){
            return ResponseEntity.ok().body(building);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Building not found");
    }
}
