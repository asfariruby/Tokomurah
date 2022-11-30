/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author user
 */
@Component
public class MessagePublisherService {
    @Autowired @Qualifier("redisPubSubTemplate")
    private RedisTemplate<String, String> template;
    
    public void publish (String message){
        template.convertAndSend("orderMessagePubSub", message);
    }
}
