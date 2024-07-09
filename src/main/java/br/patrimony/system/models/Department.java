package br.patrimony.system.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonIgnore // nao repetir infinitas vezes
    private List<Patrimony> patrimonies;

    public Department(String name, Building building, List<Patrimony> patrimonies) {
        this.name = name;
        this.building = building;
        this.patrimonies = patrimonies;
    }

    public Department(Long id, String name, Building building, List<Patrimony> patrimonies) {
        this.id = id;
        this.name = name;
        this.building = building;
        this.patrimonies = patrimonies;
    }

    public Department() {
    }

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

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public List<Patrimony> getPatrimonies() {
        return patrimonies;
    }

    public void setPatrimonies(List<Patrimony> patrimonies) {
        this.patrimonies = patrimonies;
    }
}
