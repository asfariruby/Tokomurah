/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.validator;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/**
 *
 * @author user
 */
@Component
public class RegexEmailValidator {
    private String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    
    public boolean emailPatternMatch(String email) {
        return Pattern.matches(regex, email);
    }
}
