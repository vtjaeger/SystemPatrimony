package br.patrimony.system.services;

import br.patrimony.system.dtos.requests.user.UserRequest;
import br.patrimony.system.dtos.responses.user.UserResponse;
import br.patrimony.system.models.Role;
import br.patrimony.system.models.User;
import br.patrimony.system.repositories.RoleRepository;
import br.patrimony.system.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public ResponseEntity registerUser(@RequestBody @Valid UserRequest userRequest){
        Optional<Role> roleOptional = Optional.ofNullable(roleRepository.findByFunction(userRequest.role()));
        if(roleOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }

        Role role = roleOptional.get();
        var user = new User(userRequest, role);
        return ResponseEntity.ok().body(userRepository.save(user));
    }

    public ResponseEntity getAllUser(){
        List<User> userList = userRepository.findAll();
        List<UserResponse> response = userList.stream()
                .map(user -> new UserResponse(user.getName(), user.getRole().getFunction()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }
}
