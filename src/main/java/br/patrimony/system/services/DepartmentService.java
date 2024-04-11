package br.patrimony.system.services;

import br.patrimony.system.dtos.requests.department.DepartmentRequest;
import br.patrimony.system.dtos.responses.department.DepartmentResponse;
import br.patrimony.system.dtos.responses.patrimony.PatrimonyIdNameResponse;
import br.patrimony.system.dtos.responses.patrimony.PatrimonyResponse;
import br.patrimony.system.models.Building;
import br.patrimony.system.models.Department;
import br.patrimony.system.models.Patrimony;
import br.patrimony.system.repositories.BuildingRepository;
import br.patrimony.system.repositories.DepartmentRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        Department newDepartment = new Department(departmentName, building, null);
        return ResponseEntity.ok(departmentRepository.save(newDepartment));
    }

    public ResponseEntity getAllDepartmentsResponse(){
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponse> response = departments.stream()
                .map(department -> {
                    List<Patrimony> patrimonies = department.getPatrimonies();
                    List<PatrimonyIdNameResponse> patrimonyResponse = patrimonies.stream()
                            .map(patrimony -> new PatrimonyIdNameResponse(patrimony.getId(), patrimony.getObject()))
                            .collect(Collectors.toList());

                    return new DepartmentResponse(
                            department.getBuilding().getId(),
                            department.getBuilding().getName(),
                            department.getName(),
                            patrimonyResponse
                            );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

}
