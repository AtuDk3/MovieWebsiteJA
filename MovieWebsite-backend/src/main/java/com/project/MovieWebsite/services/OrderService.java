package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.OrderDTO;
import com.project.MovieWebsite.dtos.TotalPriceByMonthDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Order;
import com.project.MovieWebsite.responses.MovieResponse;
import com.project.MovieWebsite.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderDTO order) throws DataNotFoundException;

    Order getOrder(int id);

    Page<OrderResponse> getAllOrders(PageRequest pageRequest);

    Page<OrderResponse> getAllOrderByUser(int userId, PageRequest pageRequest);

    List<TotalPriceByMonthDTO> getTotalPriceByMonth(int year);

    List<Integer> getDistinctYears();

    Order updateOrder(int id, OrderDTO order) throws DataNotFoundException;

    void deleteOrder(int id);

}
