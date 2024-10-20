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
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);

        if (utilisateur != null && passwordEncoder.matches(password, utilisateur.getPassword())) {
            return utilisateur;
        }

        return null;
    }
}
