package br.patrimony.system.controllers;

import br.patrimony.system.dtos.requests.raw.RawRequest;
import br.patrimony.system.services.RawService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("raw")
public class RawMaterialController {
    @Autowired
    private RawService rawService;

    @GetMapping
    public ResponseEntity getAll(){
        return rawService.getAllRaws();
    }

    @PostMapping
    public ResponseEntity registerRaw(@RequestBody @Valid RawRequest rawRequest){
        return rawService.registerRaw(rawRequest);
    }
}
