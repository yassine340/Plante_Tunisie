package com.plants.projet_des_plants.Web;

import com.plants.projet_des_plants.Entities.Cart;
import com.plants.projet_des_plants.Entities.DemandeCommande;
import com.plants.projet_des_plants.Entities.Utilisateur;
import com.plants.projet_des_plants.Service.CartService;
import com.plants.projet_des_plants.Service.CommandeService;
import com.plants.projet_des_plants.Service.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = "http://localhost:4200")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private CartService cartService;
    @Autowired
private CommandeService commandeService;

    @GetMapping("/byEmail")
    public ResponseEntity<Utilisateur> getUserByEmail(@RequestParam String email) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(email);
        if (utilisateur != null) {
            return ResponseEntity.ok(utilisateur);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }



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

    @PostMapping("/addCart")
    public ResponseEntity<?> addToCart(@RequestParam Long idProduit, @RequestParam Long idUtilisateur) {
        Cart saveCart = cartService.saveCart(idProduit, idUtilisateur);
        if (ObjectUtils.isEmpty(saveCart)) {
            return ResponseEntity.status(400).body("Product not added to cart");
        } else {
            return ResponseEntity.ok(saveCart);
        }
    }



    @GetMapping("/{idUtilisateur}/cart")
    public ResponseEntity<?> getCart(@PathVariable Long idUtilisateur) {
        List<Cart> carts = cartService.getCartByUtilisateur(idUtilisateur);
        if (ObjectUtils.isEmpty(carts)) {
            return ResponseEntity.status(404).body("No items found in the cart");
        }
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/updateCartQuantite")
    public String updateCartQuantite(@RequestParam String sy, @RequestParam Long cid){
        cartService.updateCartQuantite(sy,cid);
        return "redirect:/api/utilisateurs/Cart";
    }


    private Utilisateur getLoggedInUserDetails(Principal p) {
        String email = p.getName();
        return utilisateurService.getUtilisateurByEmail(email);
    }
    @GetMapping("/orders")
    public String orderPage(){
        return "/user/order";
    }

    @PostMapping("/save-commande")
    public ResponseEntity<?> saveCommande(@RequestBody DemandeCommande request, @RequestParam Long idUtilisateur) {
        // Appelle directement le service avec l'ID utilisateur
        commandeService.saveCommande(idUtilisateur, request);
        return ResponseEntity.ok("Commande enregistrée avec succès");
    }

}