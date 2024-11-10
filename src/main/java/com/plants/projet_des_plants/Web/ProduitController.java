package com.plants.projet_des_plants.Web;


import com.plants.projet_des_plants.Entities.Produit;
import com.plants.projet_des_plants.Service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {
    @Autowired
    private ProduitService produitService;


    @PostMapping("/ajoutProduit")
    public ResponseEntity<Produit> createProduit(@RequestBody Produit produit) {
        Produit createdProduit = produitService.saveProduit(produit);
        return ResponseEntity.ok(createdProduit);
    }


    @GetMapping("/affiche/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        Produit produit = produitService.getProduitById(id);
        return produit != null ? ResponseEntity.ok(produit) : ResponseEntity.notFound().build();
    }


    @GetMapping("/afficheTous")
    public List<Produit> getAllProduits() {
        return produitService.getAllProduits();
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id, @RequestBody Produit produit) {
        Produit updatedProduit = produitService.updateProduit(id, produit);
        return updatedProduit != null ? ResponseEntity.ok(updatedProduit) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
}
