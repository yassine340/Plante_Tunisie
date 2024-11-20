package com.plants.projet_des_plants.Service;

import com.plants.projet_des_plants.Entities.Utilisateur;
import com.plants.projet_des_plants.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Utilisateur registerUtilisateur(Utilisateur utilisateur) {
        utilisateur.setRole("utilisateur");
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur login(String email, String password) {
        return utilisateurRepository.findByEmail(email)
                .filter(utilisateur -> passwordEncoder.matches(password, utilisateur.getPassword()))
                .orElse(null);
    }

    public Utilisateur getUtilisateurByEmail(String email) {
        return utilisateurRepository.findByEmail(email).orElse(null);
    }
}
