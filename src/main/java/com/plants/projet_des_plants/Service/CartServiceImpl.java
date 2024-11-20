package com.plants.projet_des_plants.Service;

import com.plants.projet_des_plants.Entities.Cart;
import com.plants.projet_des_plants.Entities.Produit;
import com.plants.projet_des_plants.Entities.Utilisateur;
import com.plants.projet_des_plants.Repository.CartRepository;
import com.plants.projet_des_plants.Repository.ProduitRepository;
import com.plants.projet_des_plants.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private ProduitRepository produitRepository;

    @Override
    public Cart saveCart(Long idProduit, Long idUtilisateur) {
        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur).get();
        Produit produit = produitRepository.findById(idProduit).get();

        Cart cartStatus = cartRepository.findByUtilisateurIdAndProduitId(idUtilisateur.longValue(), idProduit.longValue());

        Cart cart = null;

        if(ObjectUtils.isEmpty(cartStatus)){
            cart = new Cart();
            cart.setProduit(produit);
            cart.setUtilisateur(utilisateur);
            cart.setQuantite(1);
            cart.setPrixTotal(1 * produit.getPrix()); // Use setter method to set the total price
            cartRepository.save(cart);
        } else {
            cart = cartStatus;
            cart.setQuantite(cart.getQuantite() + 1);
            cart.setPrixTotal(cart.getQuantite() * cart.getProduit().getPrix());
        }
        Cart savedCart = cartRepository.save(cart);
        return savedCart;
    }

    @Override
    public List<Cart> getCartByUtilisateur(Long idUtilisateur) {
        List<Cart> carts = cartRepository.findByUtilisateurId(idUtilisateur);

        int totalOrderPrice = 0;
        List<Cart> updateCarts = new ArrayList<>();
        for (Cart c : carts) {
            int prixTotal = c.getProduit().getPrix() * c.getQuantite();
            c.setPrixTotal(prixTotal);
            totalOrderPrice += prixTotal;

            c.setTotalOrderPrice(totalOrderPrice);
            updateCarts.add(c);
        }

        return updateCarts;
    }

    @Override
    public Integer getCountCart(Long idUtilisateur) {
        return cartRepository.countByUtilisateurId(idUtilisateur);
    }

    @Override
    public void updateCartQuantite(String sy, Long cid) {
        Cart cart = cartRepository.findById(cid).orElseThrow(() -> new RuntimeException("Cart not found"));
        int updatedQuantite;
        if (sy.equalsIgnoreCase("de")) {
            updatedQuantite = cart.getQuantite() - 1;
            if (updatedQuantite <= 0) {
                cartRepository.delete(cart);
            } else {
                cart.setQuantite(updatedQuantite);
                cartRepository.save(cart);
            }
        } else {
            updatedQuantite = cart.getQuantite() + 1;
            cart.setQuantite(updatedQuantite);
            cartRepository.save(cart);
        }
    }
}