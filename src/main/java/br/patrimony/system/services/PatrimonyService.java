package br.patrimony.system.services;

import br.patrimony.system.dtos.patrimony.PatrimonyRequest;
import br.patrimony.system.models.Building;
import br.patrimony.system.models.Department;
import br.patrimony.system.models.Patrimony;
import br.patrimony.system.repositories.BuildingRepository;
import br.patrimony.system.repositories.DepartmentRepository;
import br.patrimony.system.repositories.PatrimonyRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

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
        return ResponseEntity.ok().body(patrimonyList);
    }
}
