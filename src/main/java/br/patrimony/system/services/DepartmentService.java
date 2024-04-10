package br.patrimony.system.services;

import br.patrimony.system.dtos.department.DepartmentRequest;
import br.patrimony.system.models.Building;
import br.patrimony.system.models.Department;
import br.patrimony.system.repositories.BuildingRepository;
import br.patrimony.system.repositories.DepartmentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    public ResponseEntity registerDepartment(DepartmentRequest departmentRequest) {
        String buildingName = departmentRequest.buildingName();
                                                        //retorna um valor se for diferente de null.
                                                        // usar pq findByName pode retornar null
        Optional<Building> buildingOptional = Optional.ofNullable(buildingRepository.findByName(buildingName));

        if (buildingOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Building not found");
        }

        Building building = buildingOptional.get();
        String departmentName = departmentRequest.name();

        if (departmentRepository.existsByNameAndBuilding(departmentName, building)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Department already registered");
        }

        Department newDepartment = new Department(departmentName, building);
        return ResponseEntity.ok(departmentRepository.save(newDepartment));
    }

    public ResponseEntity getAllDepartmentFromBuilding(@PathVariable(value = "id") Long id){
        List<Department> departmentList = departmentRepository.findAllByBuildingId(id);
        return ResponseEntity.ok().body(departmentList);
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
}
