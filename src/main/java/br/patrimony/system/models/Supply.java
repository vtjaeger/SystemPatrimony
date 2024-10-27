package br.patrimony.system.models;

import br.patrimony.system.dtos.requests.supply.SupplyRequest;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

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
    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;
    private String status;

    public Supply(SupplyRequest supplyRequest, Building building) {
        this.item = supplyRequest.item();
        this.quantity = supplyRequest.quantity();
        this.cost = supplyRequest.cost();
        this.building = building;
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

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
