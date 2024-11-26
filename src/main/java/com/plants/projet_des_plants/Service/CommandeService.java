package com.plants.projet_des_plants.Service;

import com.plants.projet_des_plants.Entities.DemandeCommande;
import com.plants.projet_des_plants.Entities.ProduitCommande;

public interface CommandeService {
    public void saveCommande(Long utilisateur, DemandeCommande demandeCommande);

}
