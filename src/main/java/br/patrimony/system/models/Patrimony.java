package br.patrimony.system.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_patrimony")
public class Patrimony {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String object;
    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    private String responsible;
    private LocalDate acquisitionDate;
    private String numberInvoice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public LocalDate getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(LocalDate acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public String getNumberInvoice() {
        return numberInvoice;
    }

    public void setNumberInvoice(String numberInvoice) {
        this.numberInvoice = numberInvoice;
    }
}
