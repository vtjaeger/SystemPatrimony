package br.patrimony.system.services;

import br.patrimony.system.dtos.requests.raw.SupplyRequest;
import br.patrimony.system.dtos.responses.raw.SupplyResponse;
import br.patrimony.system.models.Supply;
import br.patrimony.system.repositories.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyService {
    @Autowired
    private SupplyRepository supplyRepository;
    public ResponseEntity getAllSupplys(){
        List<Supply> raws = supplyRepository.findAll();
        List<SupplyResponse> response = raws.stream()
                .map(r -> new SupplyResponse(
                        r.getId(),
                        r.getItem(),
                        r.getQuantity(),
                        r.getKilograms(),
                        r.getCost(),
                        r.getStatus())
                ).toList();
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity registerSupply(SupplyRequest supplyRequest){
        var raw = new Supply(supplyRequest);
        Supply newRaw = supplyRepository.save(raw);
        return ResponseEntity.ok().body(newRaw);
    }
}
