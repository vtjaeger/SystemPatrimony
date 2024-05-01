package br.patrimony.system.services;

import br.patrimony.system.dtos.responses.user.UserResponse;
import br.patrimony.system.models.User;
import br.patrimony.system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity getAllUser(){
        List<User> userList = userRepository.findAll();
        List<UserResponse> response = userList.stream()
                .map(user -> new UserResponse(user.getId(), user.getLogin(), user.getRole()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(userList);
    }

}
