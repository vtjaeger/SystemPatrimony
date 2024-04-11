package br.patrimony.system.services;

import br.patrimony.system.dtos.requests.patrimony.PatrimonyRequest;
import br.patrimony.system.dtos.responses.PatrimonyResponse;
import br.patrimony.system.models.Building;
import br.patrimony.system.models.Department;
import br.patrimony.system.models.Patrimony;
import br.patrimony.system.repositories.BuildingRepository;
import br.patrimony.system.repositories.DepartmentRepository;
import br.patrimony.system.repositories.PatrimonyRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

    public ResponseEntity registerPatrimony(@RequestBody @Valid PatrimonyRequest patrimonyRequest){

        boolean existBuilding = buildingRepository.existsByName(patrimonyRequest.building());
        boolean existDepartment = departmentRepository.existsByName(patrimonyRequest.department());

        if(!existBuilding || !existDepartment){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not found");
        }

        Optional<Building> buildingOptional = Optional.ofNullable(buildingRepository.findByName(patrimonyRequest.building()));
        Optional<Department> departmentOptional = Optional.ofNullable(departmentRepository.findByName(patrimonyRequest.department()));

        if (buildingOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("building not found");
        }
        if(departmentOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("department not found");
        }

        var building = buildingOptional.get();
        var department = departmentOptional.get();

        var newPatrimony = new Patrimony(patrimonyRequest, building, department);
        return ResponseEntity.ok().body(patrimonyRepository.save(newPatrimony));
    }

    public ResponseEntity getAllPatrimony(){
        List<Patrimony> patrimonyList = patrimonyRepository.findAll();

        List<PatrimonyResponse> response = patrimonyList.stream()
                .map(patrimony -> new PatrimonyResponse(
                        patrimony.getId(),
                        patrimony.getObject(),
                        patrimony.getBuilding().getName(),
                        patrimony.getDepartment().getName()))
                .collect(Collectors.toList());;

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity getAllPatrimonyFromDepartment(@PathVariable String buildingName, @PathVariable String departmentName){
                                                        //nullable pq pode retornar null
        Optional<Building> buildingOptional = Optional.ofNullable(buildingRepository.findByName(buildingName));
        Optional<Department> departmentOptional = Optional.ofNullable(departmentRepository.findByName(departmentName));

        if (buildingOptional.isEmpty() || departmentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }

        var building = buildingOptional.get();
        var department = departmentOptional.get();

        List<Patrimony> patrimonyList = patrimonyRepository.findAllByBuildingAndDepartment(building, department);

        List<PatrimonyResponse> patrimonyResponses = patrimonyList.stream()
                .map(patrimony -> new PatrimonyResponse(
                        patrimony.getId(),
                        patrimony.getObject(),
                        patrimony.getBuilding().getName(),
                        patrimony.getDepartment().getName()))
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
                patrimony.getDepartment().getName());

        return ResponseEntity.ok().body(response);
    }
}
