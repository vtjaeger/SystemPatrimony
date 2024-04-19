package br.patrimony.system.controllers;

import br.patrimony.system.dtos.responses.user.UserResponse;
import br.patrimony.system.models.Role;
import br.patrimony.system.models.User;
import br.patrimony.system.repositories.RoleRepository;
import br.patrimony.system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity getAllRoles(){
        List<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok().body(roles);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity getAllUsersByRole(@PathVariable(value = "roleId") Long id){
        List<User> users = userRepository.findByRoleId(id);
        if(users.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("nobody on this role");
        }

        List<UserResponse> response = users.stream()
                .map(user -> new UserResponse(user.getId(), user.getLogin(), user.getRole().getFunction()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }
}