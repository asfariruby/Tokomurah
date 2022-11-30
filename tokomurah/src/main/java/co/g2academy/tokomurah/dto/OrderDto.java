/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.dto;

import co.g2academy.tokomurah.model.Order;
import co.g2academy.tokomurah.model.OrderItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author user
 */
public class OrderDto {

    private Integer storeFrontOrderId;
    private Integer userId;
    private Date transactionDate;
    private String status;
    private Integer totalPrice;
    private List<OrderItemDto> items;

    public OrderDto(Order o) {
        storeFrontOrderId = o.getId();
        userId = o.getUser().getId();
        transactionDate = o.getTransactionDate();
        status = o.getStatus();
        items = new ArrayList<>();
        totalPrice = o.getTotalPrice();
        for (OrderItem item : o.getItems()) {
            items.add(new OrderItemDto(item));
        }
    }

    public Integer getStoreFrontOrderId() {
        return storeFrontOrderId;
    }

    public void setStoreFrontOrderId(Integer storeFrontOrderId) {
        this.storeFrontOrderId = storeFrontOrderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

}
