/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.controller;

import co.g2academy.tokomurah.model.User;
import co.g2academy.tokomurah.repository.DummyLoginRepository;
import co.g2academy.tokomurah.repository.UserRepository;
import co.g2academy.tokomurah.validator.RegexEmailValidator;
import co.g2academy.tokomurah.validator.RegexPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author user
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private RegexEmailValidator emailValidator;
    @Autowired
    private RegexPasswordValidator passwordValidator;
    //password encoding, which is basically not storing the password in plaintext.
    private PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();
    
    //post mapping annotation
    @PostMapping("/register")
    //create a register method with the type of ResponseEntity
    //ResponseEntity represents the whole HTTP response: status code, headers, and body. As a result, we can use it to fully configure the HTTP response.
    //@RequestBody annotation maps the HttpRequest body to a transfer or domain object, enabling automatic deserialization of the inbound HttpRequest body onto a Java object.
    public ResponseEntity register(@RequestBody User user) {
        //declare userFromDB variable with the type of User that is comprised by a method inside user repository to find a username
        User userFromDb = repository.findUserByUsername(user.getUsername());
        //if the username is not found, and  the email and password vaidation using regex is valid, then save the user
        if (userFromDb == null
                && emailValidator.emailPatternMatch(user.getUsername())
                && passwordValidator.passwordPatternMatch(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
        } else {
            return ResponseEntity.badRequest()
                    .body("email or password invalid");
        }
        return ResponseEntity.ok().body("OK");
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        User userFromDb = repository.findUserByUsername(user.getUsername());
        // if user is found, and the password is the same, then user logged in, and login success
        if (userFromDb != null
                && userFromDb.getPassword().equals(user.getPassword())) {
            DummyLoginRepository.setLoggedInUser(userFromDb);
            return ResponseEntity.ok().body("Login Success");
        }
        return ResponseEntity.badRequest().body("Login Fail");
    }
}
