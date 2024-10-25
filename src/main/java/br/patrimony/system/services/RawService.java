package br.patrimony.system.services;

import br.patrimony.system.dtos.requests.raw.RawRequest;
import br.patrimony.system.dtos.responses.raw.RawResponse;
import br.patrimony.system.models.RawMaterial;
import br.patrimony.system.repositories.RawMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RawService {
    @Autowired
    private RawMaterialRepository rawMaterialRepository;
    public ResponseEntity getAllRaws(){
        List<RawMaterial> raws = rawMaterialRepository.findAll();
        List<RawResponse> response = raws.stream()
                .map(r -> new RawResponse(
                        r.getId(),
                        r.getQuantity(),
                        r.getKilograms(),
                        r.getCost(),
                        r.getStatus())
                ).toList();
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity registerRaw(RawRequest rawRequest){
        var raw = new RawMaterial(rawRequest);
        RawMaterial newRaw = rawMaterialRepository.save(raw);
        return ResponseEntity.ok().body(newRaw);
    }
}
