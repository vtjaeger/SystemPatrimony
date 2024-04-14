package br.patrimony.system.controllers;

import br.patrimony.system.models.Role;
import br.patrimony.system.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public ResponseEntity getAllRoles(){
        List<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok().body(roles);
    }
}
