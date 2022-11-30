/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.controller;

import co.g2academy.tokomurah.model.Product;
import co.g2academy.tokomurah.model.ShoppingCart;
import co.g2academy.tokomurah.model.ShoppingCartItem;
import co.g2academy.tokomurah.model.User;
import co.g2academy.tokomurah.repository.DummyLoginRepository;
import co.g2academy.tokomurah.repository.ProductRepository;
import co.g2academy.tokomurah.repository.ShoppingCartRepository;
import co.g2academy.tokomurah.repository.UserRepository;
import co.g2academy.tokomurah.service.CheckoutService;
import co.g2academy.tokomurah.view.AddToCart;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.hibernate.event.spi.PostCollectionRecreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author user
 *
 *
 */
//controller annotation
@RestController
@RequestMapping("/api")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  CheckoutService checkoutService;

    @PostMapping("/add-to-cart")
    public ResponseEntity addToCart(
            @RequestBody AddToCart atc, Principal principal) {
        User u = userRepository.findUserByUsername(principal.getName());
        //make an opt object that is an Optional List, that finds product Id that added to cart
        Optional<Product> opt = productRepository.findById(atc.getProductId());
        //if the product id is not found inside the cart, then return product not found
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body("Product not found");
        }
        Product p = opt.get();
        //cek apakah sudah ada dalam tabel
        ShoppingCart cart = cartRepository.getShoppingCartByUserAndStatus(u, "ACTIVE");
        // if the cart is not already exist, then create new make cart as new ShoppingCart, set use, set status,  initialize new items list,and set items
        if (cart == null) {
            cart = new ShoppingCart();
            cart.setUser(u);
            cart.setStatus("ACTIVE");
            List<ShoppingCartItem> items = new ArrayList<>();
            cart.setItems(items);
        }
        // iterating items in shopping cart. If item id in shopping cart is equal to item that added to cart, then the item is shopping cart item
        ShoppingCartItem item = null;
        for (ShoppingCartItem sci : cart.getItems()) {
            if (sci.getProduct().getId().equals(atc.getProductId())) {
                item = sci;
                break;
            }
        }
        //if item is null, then make that item as new shopping cart item, else set the quantity.
        if (item == null) {
            item = new ShoppingCartItem();
            item.setCart(cart);
            item.setProduct(p);
            item.setQuantity(atc.getQuantity());
            item.setPrice(p.getPrice());
            cart.getItems().add(item);
        } else {
            item.setQuantity(item.getQuantity() + atc.getQuantity());
        }
        cartRepository.save(cart);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/cart")
    public ResponseEntity getCart(Principal principal) {
        User u = userRepository.findUserByUsername(
                principal.getName());
        ShoppingCart cart
                = cartRepository.getShoppingCartByUserAndStatus(u, "ACTIVE");
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/checkout")
    public ResponseEntity checkout(Principal principal) throws JsonProcessingException {
        User u = userRepository.findUserByUsername(
                principal.getName());
        ShoppingCart cart
                = cartRepository.getShoppingCartByUserAndStatus(u, "ACTIVE");
        if (cart == null) {
            return ResponseEntity.badRequest().body("Cart not found");
        }
        checkoutService.checkout(cart);
        cart.setStatus("PROCESSED");
        cart.setTransactionDate(new Date());
        cartRepository.save(cart);

        //TODO send ShoppingCart Order Fullfillment
        return ResponseEntity.ok("OK");
    }

}
