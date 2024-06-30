package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.OrderDTO;
import com.project.MovieWebsite.dtos.TotalPriceByMonthDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.Order;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.repositories.OrderRepository;
import com.project.MovieWebsite.repositories.UserRepository;
import com.project.MovieWebsite.responses.MovieResponse;
import com.project.MovieWebsite.responses.OrderResponse;
import com.project.MovieWebsite.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
                        .user(existingUser)
                        .price(orderDTO.getPrice()).
                        build();
        return orderRepository.save(newOrder);
    }

    @Override
    public Order getOrder(int id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public Page<OrderResponse> getAllOrders(PageRequest pageRequest) {
        Page<Order> ordersPage = orderRepository.getAllOrders(pageRequest);
        return mapToOrderResponsePage(ordersPage);
    }

    @Override
    public Page<OrderResponse> getAllOrderByUser(int userId, PageRequest pageRequest) {
        Page<Order> ordersPage = orderRepository.getAllOrderByUser(userId, pageRequest);
        return mapToOrderResponsePage(ordersPage);
    }

    public List<TotalPriceByMonthDTO> getTotalPriceByMonth(int year) {
        return orderRepository.getTotalPriceByMonth(year);
    }

    public List<Integer> getDistinctYears() {
        return orderRepository.findDistinctYears();
    }

    private Page<OrderResponse> mapToOrderResponsePage(Page<Order> ordersPage) {
        return ordersPage.map(order -> {
            OrderResponse orderResponse = OrderResponse.builder()
                    .id(order.getId())
                    .userId(order.getUser().getId())
                    .userName(order.getUser().getFullName())
                    .price(order.getPrice())
                    .orderDate(convertToDate(order.getOrderDate()))
                    .build();
            return orderResponse;
        });
    }

    private static Date convertToDate(LocalDateTime dateTime) {
        return dateTime != null ? Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
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
