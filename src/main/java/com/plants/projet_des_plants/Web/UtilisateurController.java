package com.plants.projet_des_plants.Web;

import com.plants.projet_des_plants.Entities.Utilisateur;
import com.plants.projet_des_plants.Service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Utilisateur utilisateur) {
        Utilisateur newUser = utilisateurService.registerUtilisateur(utilisateur);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Utilisateur utilisateur) {
        Utilisateur loggedInUser = utilisateurService.login(utilisateur.getEmail(), utilisateur.getPassword());

        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}
