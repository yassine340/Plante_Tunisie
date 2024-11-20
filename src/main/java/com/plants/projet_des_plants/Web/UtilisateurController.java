package com.plants.projet_des_plants.Web;

import com.plants.projet_des_plants.Entities.Cart;
import com.plants.projet_des_plants.Entities.Utilisateur;
import com.plants.projet_des_plants.Service.CartService;
import com.plants.projet_des_plants.Service.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String addToCart(@RequestParam Long idProduit, @RequestParam Long idUtilisateur, HttpSession session) {
        Cart saveCart = cartService.saveCart(idProduit, idUtilisateur);
        if (ObjectUtils.isEmpty(saveCart)) {
            session.setAttribute("errorMsg", "Product not added to cart");
        } else {
            session.setAttribute("successMsg", "Product added to cart");
        }
        return "redirect:/login";
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
}