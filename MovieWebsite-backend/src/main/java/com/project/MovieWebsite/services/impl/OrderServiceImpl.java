package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.OrderDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Order;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.repositories.OrderRepository;
import com.project.MovieWebsite.repositories.UserRepository;
import com.project.MovieWebsite.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public Order createOrder(OrderDTO orderDTO) throws DataNotFoundException{
        User existingUser= userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user type with id: "+orderDTO.getUserId()));
        Order newOrder= Order.builder()
                        .user(existingUser). build();

        return orderRepository.save(newOrder);
    }

    @Override
    public Order getOrder(int id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrder(int id, OrderDTO orderDTO) throws DataNotFoundException{
        Order existingOrder= getOrder(id);
        if(existingOrder!=null){
            User existingUser= userRepository.findById(orderDTO.getUserId())
                    .orElseThrow(() -> new DataNotFoundException("Cannot find user type with id: "+orderDTO.getUserId()));
            existingOrder.setUser(existingUser);
            return orderRepository.save(existingOrder);

        }
        return null;
    }

    @Override
    public void deleteOrder(int id) {
        Optional<Order> optionalOrder= orderRepository.findById(id);
        if(optionalOrder.isPresent()){
            orderRepository.deleteById(id);
        }

    }
}
