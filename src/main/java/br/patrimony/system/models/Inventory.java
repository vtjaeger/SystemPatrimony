package br.patrimony.system.models;

import br.patrimony.system.dtos.requests.inventory.InventoryRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_inventory")
public class Inventory {
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
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    public Inventory(InventoryRequest inventoryRequest, Building building) {
        this.item = inventoryRequest.item();
        this.quantity = inventoryRequest.quantity();
        this.cost = inventoryRequest.cost();
        this.building = building;
        this.status = Status.AVAILABLE;
    }

    public Inventory() {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
