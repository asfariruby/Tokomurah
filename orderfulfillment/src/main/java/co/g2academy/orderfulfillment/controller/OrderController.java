/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.orderfulfillment.controller;

import co.g2academy.orderfulfillment.model.Order;
import co.g2academy.orderfulfillment.repository.OrderRepository;
import org.aspectj.apache.bcel.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author user
 */
@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderRepository repository;
    
    @PutMapping("/deliver/{id}")
    public String deliverOrder(@PathVariable Integer id){
        Order order = repository.findById(id).get();
        order.setStatus("DELIVERED");
        repository.save(order);
        //TODO senda data back to Redis and Tokomurah
        //the order status accordingly
        return "OK";
        
    }
    
}
