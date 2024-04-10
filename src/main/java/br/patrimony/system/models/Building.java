package br.patrimony.system.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_building")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
