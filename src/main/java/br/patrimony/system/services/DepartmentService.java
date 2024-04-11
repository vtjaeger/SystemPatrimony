package br.patrimony.system.services;

import br.patrimony.system.dtos.requests.department.DepartmentRequest;
import br.patrimony.system.models.Building;
import br.patrimony.system.models.Department;
import br.patrimony.system.repositories.BuildingRepository;
import br.patrimony.system.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

}
