/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.security.controller;

import co.g2academy.tokomurah.security.Service.JwtUserDetailService;
import co.g2academy.tokomurah.security.filter.JwtRequestFilter;
import co.g2academy.tokomurah.security.model.JwtRequest;
import co.g2academy.tokomurah.security.model.JwtResponse;
import co.g2academy.tokomurah.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author user
 */
//Expose a POST API /authenticate using the JwtAuthenticationController. 
//The POST API gets username and password in the body- Using Spring Authentication Manager we authenticate the username and password.
//If the credentials are valid, a JWT token is created using the JWTTokenUtil and provided to the client.

@RestController
@CrossOrigin
@RequestMapping("/api")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil tokenUtil;
    @Autowired
    private JwtUserDetailService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> createToken(
            @RequestBody JwtRequest request) throws Exception {
        authenticate(request.getUsername(),
                request.getPassword());
        UserDetails userDetails
                = userService.loadUserByUsername(request.getUsername());
        String token = tokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) 
        throws Exception {
            try {
                UsernamePasswordAuthenticationToken authToken
                        = new UsernamePasswordAuthenticationToken(
                                username, password);
                authenticationManager.authenticate(authToken);
            } catch (DisabledException e) {
                throw new Exception("USER_DISABLED", e);
            } catch (BadCredentialsException e) {
                throw new Exception("INVALID_CREDENTIALS", e);
            }
        }
    }