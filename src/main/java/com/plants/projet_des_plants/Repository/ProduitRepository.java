package com.plants.projet_des_plants.Repository;

import com.plants.projet_des_plants.Entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit,Long> {

}
