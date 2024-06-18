package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select or from Order or")
    Page<Order> getAllOrders(Pageable pageable);

    @Query("select or from Order or where or.user.id = :userId")
    Page<Order> getAllOrderByUser(@Param("userId") int userId, Pageable pageable);
}