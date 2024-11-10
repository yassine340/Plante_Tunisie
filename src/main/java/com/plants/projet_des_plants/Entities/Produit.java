package com.plants.projet_des_plants.Entities;
import jakarta.persistence.*;

import lombok.*;

@Entity @NoArgsConstructor @AllArgsConstructor
@Data
public class Produit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private String image;
    private String prix;
    private String quantite;
@ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;
}
