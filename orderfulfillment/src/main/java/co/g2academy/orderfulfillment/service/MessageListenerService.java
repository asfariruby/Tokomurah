/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.orderfulfillment.service;

import co.g2academy.orderfulfillment.model.Order;
import co.g2academy.orderfulfillment.model.OrderItem;
import co.g2academy.orderfulfillment.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author user
 */
@Service
public class MessageListenerService implements MessageListener{
    @Autowired @Qualifier ("redisPubSubTemplate")
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private OrderRepository repository;
    private ObjectMapper mapper = new JsonMapper();
    
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try{
            String json = new String(message.getBody());
            System.out.println("Getting JSON Message: " + json);
            Order order = mapper.readValue(json, new TypeReference<Order>(){});
            for (OrderItem item: order.getItems()){
                item.setOrder(order);
            }
            repository.save(order);
        }catch(JsonProcessingException ex){
            ex.printStackTrace();   
        }
    }
}
