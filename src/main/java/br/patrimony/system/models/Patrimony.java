package br.patrimony.system.models;

import br.patrimony.system.dtos.requests.patrimony.PatrimonyRequest;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_patrimony")
public class Patrimony {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String object;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @Enumerated(EnumType.STRING)
    private UserRole responsible;
    private LocalDate acquisitionDate;
    private LocalDate revisionDate;

    public Patrimony(PatrimonyRequest patrimonyRequest, Category category, Building building, Department department) {
        this.object = patrimonyRequest.object();
        this.category = category;
        this.building = building;
        this.department = department;
        this.acquisitionDate = LocalDate.now();
    }

    public Patrimony(String object, Category category, Building building, Department department, UserRole responsible,
                     LocalDate acquisitionDate, LocalDate revisionDate) {
        this.object = object;
        this.category = category;
        this.building = building;
        this.department = department;
        this.responsible = responsible;
        this.acquisitionDate = acquisitionDate;
        this.revisionDate = revisionDate;
    }

    public Patrimony() {
    }

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

    public UserRole getResponsible() {
        return responsible;
    }

    public void setResponsible(UserRole responsible) {
        this.responsible = responsible;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(LocalDate acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public LocalDate getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(LocalDate revisionDate) {
        this.revisionDate = revisionDate;
    }
}
