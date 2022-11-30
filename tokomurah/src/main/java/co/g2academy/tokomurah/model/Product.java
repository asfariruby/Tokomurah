/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "t_product")
public class Product implements Serializable{
//serialization is the conversion of a Java object into a static stream (sequence) of bytes, 
//which we can then save to a database or transfer over a network.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
   @Column(length = 100, nullable = false)
    private String name;
   @Column(length = 255, nullable = false)
    private String description;
   @Column(nullable = false)
    private Integer price;
   @Column(nullable = false)
    private Integer stock;
   @ManyToOne
   @JoinColumn (name = "user_id", nullable = false)
   //user_id is newly created join column that contain user id from class User,
   //and make user id as foreign key in t_product
   @JsonIgnore
   private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

   
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer quantity) {
        this.stock = quantity;
    }
    
}
