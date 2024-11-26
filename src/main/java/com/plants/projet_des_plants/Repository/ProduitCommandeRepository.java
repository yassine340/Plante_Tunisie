package com.plants.projet_des_plants.Repository;

import com.plants.projet_des_plants.Entities.ProduitCommande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitCommandeRepository extends JpaRepository<ProduitCommande,Long> {
}
