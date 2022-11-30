/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.repository;

import co.g2academy.tokomurah.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
@Repository
//Repository annotation is a specialization of @Component annotation, 
//so Spring Repository classes are autodetected by spring framework through classpath scanning. 
public interface OrderRepository extends JpaRepository<Order, Integer>{
    
}
