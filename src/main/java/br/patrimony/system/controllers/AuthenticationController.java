package br.patrimony.system.controllers;

import br.patrimony.system.dtos.requests.login.LoginRequest;
import br.patrimony.system.dtos.requests.user.UserRequest;
import br.patrimony.system.dtos.responses.token.TokenResponse;
import br.patrimony.system.models.User;
import br.patrimony.system.repositories.UserRepository;
import br.patrimony.system.security.TokenService;
import br.patrimony.system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserRequest userRequest){
        // se login ja existe
        if(userRepository.findByLogin(userRequest.login()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(userRequest.password());
        var user = new User(userRequest.login(), encryptedPassword, userRequest.role());

        return ResponseEntity.ok().body(userRepository.save(user));
    }


}
