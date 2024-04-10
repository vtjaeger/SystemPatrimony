package br.patrimony.system.controllers;

import br.patrimony.system.dtos.patrimony.PatrimonyRequest;
import br.patrimony.system.services.PatrimonyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("patrimony")
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
}
