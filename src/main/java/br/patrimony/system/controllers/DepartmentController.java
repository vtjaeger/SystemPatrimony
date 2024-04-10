package br.patrimony.system.controllers;

import br.patrimony.system.dtos.department.DepartmentRequest;
import br.patrimony.system.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity registerDepartment(@RequestBody @Valid DepartmentRequest departmentRequest){
        return departmentService.registerDepartment(departmentRequest);
    }

}
