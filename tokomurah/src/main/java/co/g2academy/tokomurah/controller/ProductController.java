/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.controller;

import co.g2academy.tokomurah.dao.jdbc.ProductSelectDaoJdbc;
import co.g2academy.tokomurah.dao.springjdbc.ProductDaoSpringJdbc;
import co.g2academy.tokomurah.model.Product;
import co.g2academy.tokomurah.model.User;
import co.g2academy.tokomurah.repository.ProductRepository;
import co.g2academy.tokomurah.repository.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    

    @GetMapping("/product")
    private List<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productRepository.findById(id).get();
    }

    @PostMapping("/save/product")
    public String save(@RequestBody Product product, Principal principal) {
        User u = userRepository.findUserByUsername(principal.getName());
        product.setUser(u);
        productRepository.save(product);
        return "success";
    }

    @PutMapping("/update/product/{id}")
    public ResponseEntity update(@RequestBody Product product, Principal principal) {
        Optional<Product> opt
                = productRepository.findById(product.getId());
        if (!opt.isEmpty()) {
            Product pFromDb = opt.get();
            if (pFromDb.getUser().getUsername()
                    .equals(principal.getName()));
            pFromDb.setName(product.getName());
            pFromDb.setPrice(product.getPrice());
            pFromDb.setStock(product.getStock());
            productRepository.save(pFromDb);
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.badRequest().body("Product not found");
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id, Principal principal) {
        Optional<Product> opt
                = productRepository.findById(id);
        if (!opt.isEmpty()) {
            Product pFromDb = opt.get();
            if (pFromDb.getUser().getUsername().equals(principal.getName())) {
                productRepository.deleteById(id);
                return ResponseEntity.ok("success");
            }
        }
    return ResponseEntity.badRequest()
            .body("Product not found");

//    @DeleteMapping("/product")
//    public String delete() {
//        repository.deleteAll();
//        return "success";
//    }
}
}
