package br.patrimony.system.models;

import br.patrimony.system.dtos.building.BuildingRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_building")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Building(BuildingRequest buildingRequest) {
        this.name = buildingRequest.name();
    }

    public Building() {
    }
}
