/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.repository;

import co.g2academy.tokomurah.model.User;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
@Repository
public class DummyLoginRepository {
    private static User loggedInUser;
    
    public static User getLoggedInUser() {
        return loggedInUser;
    }
    
    public static void setLoggedInUser(User logged) {
       DummyLoginRepository.loggedInUser = logged;
    }
}
