package br.patrimony.system.models;

import br.patrimony.system.dtos.requests.raw.RawRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_raw")
public class RawMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double quantity;
    private String kilograms;
    private Double cost;
    private String status;

    public RawMaterial(RawRequest rawRequest) {
        this.quantity = rawRequest.quantity();
        this.kilograms = rawRequest.kilograms();
        this.cost = rawRequest.cost();
        this.status = rawRequest.status();
    }

    public RawMaterial(Long id, Double quantity, String kilograms, Double cost, String status) {
        this.id = id;
        this.quantity = quantity;
        this.kilograms = kilograms;
        this.cost = cost;
        this.status = status;
    }

    public RawMaterial() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getKilograms() {
        return kilograms;
    }

    public void setKilograms(String kilograms) {
        this.kilograms = kilograms;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
