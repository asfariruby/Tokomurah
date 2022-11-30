/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.orderfulfillment.repository;

import co.g2academy.orderfulfillment.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author user
 */
public interface OrderRepository extends JpaRepository<Order, Integer>{
    
}
