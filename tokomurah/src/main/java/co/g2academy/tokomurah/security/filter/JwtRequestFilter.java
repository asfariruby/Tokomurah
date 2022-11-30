/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.security.filter;

import co.g2academy.tokomurah.security.Service.JwtUserDetailService;
import co.g2academy.tokomurah.security.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author user
 */
//The JwtRequestFilter extends the Spring Web Filter OncePerRequestFilter class. 
//For any incoming request this Filter class gets executed. It checks if the request has a valid JWT token. 
//If it has a valid JWT Token then it sets the Authentication in the context, to specify that the current user is authenticated.
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailService userService;
    @Autowired
    private JwtTokenUtil tokenUtil;
    
    @Override
    protected void doFilterInternal (HttpServletRequest request, 
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        
        if((requestTokenHeader != null)
            && requestTokenHeader.startsWith("Bearer")){
            token = requestTokenHeader.substring(7);
            try{
            username = tokenUtil.getUsernameFromToken(token);
            }catch(ExpiredJwtException ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        
        if(username != null
                && SecurityContextHolder
                .getContext().getAuthentication() == null){
            UserDetails userDetails = 
                    userService.loadUserByUsername(username);
            if(tokenUtil.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken upToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                upToken.setDetails(
                    new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext()
                        .setAuthentication(upToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
