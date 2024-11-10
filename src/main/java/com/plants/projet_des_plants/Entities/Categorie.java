package com.plants.projet_des_plants.Entities;

import jakarta.persistence.*;

import lombok.*;

import java.util.List;

@Entity @NoArgsConstructor @AllArgsConstructor
@Data

public class Categorie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @OneToMany(mappedBy = "categorie")
    private List<Produit> plantes;



}
