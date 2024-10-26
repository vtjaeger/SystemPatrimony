package br.patrimony.system.models;

import br.patrimony.system.dtos.requests.raw.SupplyRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_supply")
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String item;
    private int quantity;
    private Double kilograms;
    private Double cost;
    private String status;

    public Supply(SupplyRequest supplyRequest) {
        this.item = supplyRequest.item();
        this.quantity = supplyRequest.quantity();
        this.kilograms = supplyRequest.kilograms();
        this.cost = supplyRequest.cost();
        this.status = supplyRequest.status();
    }

    public Supply() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getKilograms() {
        return kilograms;
    }

    public void setKilograms(Double kilograms) {
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
