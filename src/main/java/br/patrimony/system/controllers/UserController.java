package br.patrimony.system.controllers;

import br.patrimony.system.dtos.requests.user.UserRequest;
import br.patrimony.system.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity getAllUsers(){
        return userService.getAllUser();
    }

}
