package br.patrimony.system.services;

import br.patrimony.system.dtos.requests.patrimony.PatrimonyRequest;
import br.patrimony.system.dtos.responses.patrimony.PatrimonyResponse;
import br.patrimony.system.models.Building;
import br.patrimony.system.models.Department;
import br.patrimony.system.models.Patrimony;
import br.patrimony.system.repositories.BuildingRepository;
import br.patrimony.system.repositories.DepartmentRepository;
import br.patrimony.system.repositories.PatrimonyRepository;
import br.patrimony.system.services.extra.ResponsibleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatrimonyService {
    @Autowired
    private PatrimonyRepository patrimonyRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private ResponsibleService responsibleService;

    public ResponseEntity registerPatrimony(@RequestBody @Valid PatrimonyRequest patrimonyRequest){
        Optional<Building> buildingOptional = Optional.ofNullable(buildingRepository.findByName(patrimonyRequest.building()));
        List<Department> departments = departmentRepository.findAllByName(patrimonyRequest.department());

        if (buildingOptional.isEmpty() || departments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("building or department not found");
        }

        var building = buildingOptional.get();

        Department departamentoCorreto = null;

        if(departments.size() > 1){
            for(Department department : departments){
                if(department.getBuilding().getId().equals(building.getId())){
                    departamentoCorreto = department;
                    break;
                }
            }
        } if (departments.size() == 1) {
            departamentoCorreto = departments.get(0);
        }

        if (!building.getDepartments().contains(departamentoCorreto)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("department not found in this building");
        }

        var responsible = responsibleService.determinateResponsible(departamentoCorreto.getName());

        var newPatrimony = new Patrimony(patrimonyRequest, building, departamentoCorreto);
        newPatrimony.setResponsible(responsible);

        patrimonyRepository.save(newPatrimony);

        PatrimonyResponse response = new PatrimonyResponse(
                newPatrimony.getId(),
                newPatrimony.getObject(),
                newPatrimony.getBuilding().getName(),
                newPatrimony.getDepartment().getName(),
                newPatrimony.getResponsible()
        );
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity getAllPatrimony(){
        List<Patrimony> patrimonyList = patrimonyRepository.findAll();

        List<PatrimonyResponse> response = patrimonyList.stream()
                .map(patrimony -> new PatrimonyResponse(
                        patrimony.getId(),
                        patrimony.getObject(),
                        patrimony.getBuilding().getName(),
                        patrimony.getDepartment().getName(),
                        patrimony.getResponsible()))
                .collect(Collectors.toList());;

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity getAllPatrimonyFromDepartment(@PathVariable String buildingName, @PathVariable String departmentName){
        // nullable pq pode retornar null
        Optional<Building> buildingOptional = Optional.ofNullable(buildingRepository.findByName(buildingName));
        List<Department> departments = departmentRepository.findAllByName(departmentName);

        if (buildingOptional.isEmpty() || departments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("building or department not found");
        }

        Building building = buildingOptional.get();

        // filtrar lista de departamentos para o building passado
        List<Department> filteredDepartments = departments.stream()
                .filter(department -> department.getBuilding().equals(building))
                .collect(Collectors.toList());

        if (filteredDepartments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("department not found this building");
        }

        List<Patrimony> patrimonyList = new ArrayList<>();

        for (Department department : filteredDepartments) {
            List<Patrimony> departmentPatrimonies = patrimonyRepository.findAllByBuildingAndDepartment(building, department);
            patrimonyList.addAll(departmentPatrimonies);
        }

        List<PatrimonyResponse> patrimonyResponses = patrimonyList.stream()
                .map(patrimony -> new PatrimonyResponse(
                        patrimony.getId(),
                        patrimony.getObject(),
                        patrimony.getBuilding().getName(),
                        patrimony.getDepartment().getName(),
                        patrimony.getResponsible()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(patrimonyResponses);
    }

    public ResponseEntity getByPatrimonyName(@PathVariable String patrimonyName){
        Optional<Patrimony> patrimonyOptional = Optional.ofNullable(patrimonyRepository.findByObject(patrimonyName));
        if(patrimonyOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("patrimony not found");
        }
        var patrimony = patrimonyOptional.get();
        var response = new PatrimonyResponse(patrimony.getId(), patrimony.getObject(), patrimony.getBuilding().getName(),
                patrimony.getDepartment().getName(), patrimony.getResponsible());

        return ResponseEntity.ok().body(response);
    }
}
