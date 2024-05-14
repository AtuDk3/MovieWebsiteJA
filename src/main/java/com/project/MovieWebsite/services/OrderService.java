package com.project.MovieWebsite.services;

import com.project.MovieWebsite.models.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);

    Order getOrderById(int orderId);

    List<Order> getAllOrder();

    Order updateOrder(int orderId, Order order);

    void deleteOrder(int orderId);
}
