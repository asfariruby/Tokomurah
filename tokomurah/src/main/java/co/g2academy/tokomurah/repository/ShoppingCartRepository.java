/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.repository;

import co.g2academy.tokomurah.model.ShoppingCart;
import co.g2academy.tokomurah.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
// repository annotation
@Repository
// repository is an instance that extends Java Persistence API Repository
//T: Domain type that repository manages (Generally the Entity/Model class name)
//ID: Type of the id of the entity that repository manages (Generally the wrapper class of your @Id that is created inside the Entity/Model class)
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer>{
    public ShoppingCart getShoppingCartByUserAndStatus(
            User user, String status);
}
