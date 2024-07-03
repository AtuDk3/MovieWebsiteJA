package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.OrderDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderDTO order) throws DataNotFoundException;

    Order getOrder(int id);

    List<Order> getAllOrders();

    Order updateOrder(int id, OrderDTO order) throws DataNotFoundException;

    void deleteOrder(int id);

}
