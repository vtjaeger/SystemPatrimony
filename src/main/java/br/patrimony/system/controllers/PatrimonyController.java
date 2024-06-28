package br.patrimony.system.controllers;

import br.patrimony.system.dtos.requests.patrimony.PatrimonyRequest;
import br.patrimony.system.services.PatrimonyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("patrimony")
@CrossOrigin(origins = "*")
public class PatrimonyController {
    @Autowired
    private PatrimonyService patrimonyService;

    @PostMapping
    public ResponseEntity registerPatrimony(@RequestBody @Valid PatrimonyRequest patrimonyRequest){
        return patrimonyService.registerPatrimony(patrimonyRequest);
    }

    @GetMapping
    public ResponseEntity getAllPatrimony(){
        return patrimonyService.getAllPatrimony();
    }

    @GetMapping("/{patrimonyName}")
    public ResponseEntity getByPatrimony(@PathVariable String patrimonyName){
        return patrimonyService.getByPatrimonyName(patrimonyName);
    }

    @GetMapping("/{buildingName}/{departmentName}")
    public ResponseEntity getAllPatrimonyFromDepartment(@PathVariable String buildingName, @PathVariable String departmentName){
        return patrimonyService.getAllPatrimonyFromDepartment(buildingName, departmentName);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity getByCategory(@PathVariable String category) {
        return patrimonyService.getByCategory(category);
    }

    @GetMapping("/acquired-before/{date}")
    public ResponseEntity getAcquisitionBefore(@PathVariable String date){
        LocalDate localDate = LocalDate.parse(date);
        return patrimonyService.getAllAcquiredBefore(localDate);
    }
}
