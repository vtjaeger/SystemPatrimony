package br.patrimony.system.services;

import br.patrimony.system.dtos.requests.building.BuildingRequest;
import br.patrimony.system.dtos.responses.building.BuildingDepartmentResponse;
import br.patrimony.system.models.Building;
import br.patrimony.system.models.Department;
import br.patrimony.system.repositories.BuildingRepository;
import br.patrimony.system.repositories.DepartmentRepository;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public ResponseEntity registerBuilding(@RequestBody @Valid BuildingRequest buildingRequest){
        if(buildingRepository.existsByName(buildingRequest.name())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Building already registered");
        }
        var newBuilding = new Building(buildingRequest);
        return ResponseEntity.ok().body(buildingRepository.save(newBuilding));
    }

    public ResponseEntity getAllBuildings(){
        List<Building> buildingList = buildingRepository.findAll();

        List<BuildingDepartmentResponse> buildingDepartments = new ArrayList<>();

        for(Building building : buildingList) {
            List<Department> departments = building.getDepartments();

            List<String> departmentsName = departments.stream()
                    .map(Department::getName)
                    .collect(Collectors.toList());

            BuildingDepartmentResponse response = new BuildingDepartmentResponse(
                    building.getId(),
                    building.getName(),
                    departmentsName
            );
            buildingDepartments.add(response);
        }
        return ResponseEntity.ok().body(buildingDepartments);
    }

    public ResponseEntity getOneBuilding(@PathVariable(value = "id") Long id) {
        Optional<Building> buildingOptional = buildingRepository.findById(id);
        if (buildingOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Building not found");
        }

        Building building = buildingOptional.get();
        List<String> departmentNames = building.getDepartments().stream()
                .map(Department::getName)
                .collect(Collectors.toList());

        BuildingDepartmentResponse buildingDepartments = new BuildingDepartmentResponse(
                building.getId(),
                building.getName(),
                departmentNames
        );
        return ResponseEntity.ok().body(buildingDepartments);
    }

    public ResponseEntity getOneDepartmentFromBuilding(@PathVariable String buildingName, String departmentName){
        var building = buildingRepository.findByName(buildingName);
        var department = departmentRepository.findByName(departmentName);

        if(building == null || department == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("erro");
        }

        var response = departmentRepository.findByNameAndBuilding(departmentName, building);
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity getAllDepartmentFromBuilding(@PathVariable(value = "id") Long id){
        List<Department> departmentList = departmentRepository.findAllByBuildingId(id);
        return ResponseEntity.ok().body(departmentList);
    }
}
