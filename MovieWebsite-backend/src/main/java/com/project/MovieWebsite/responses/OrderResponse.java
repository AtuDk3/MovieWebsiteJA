package com.project.MovieWebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieWebsite.models.MovieView;
import com.project.MovieWebsite.models.Order;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private int id;

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("price")
    private double price;

    @JsonProperty("order_date")
    private Date orderDate;

    public static List<OrderResponse> fromListOrder(List<Order> listOder){

        List<OrderResponse> listOrderResponses = new ArrayList<>();
        for (Order order: listOder){
            OrderResponse orderResponse= OrderResponse.builder()
                    .id(order.getId())
                    .userId(order.getUser().getId())
                    .userName(order.getUser().getFullName())
                    .price(order.getPrice())
                    .orderDate(convertToDate(order.getOrderDate()))
                    .build();
            listOrderResponses.add(orderResponse);
        }
        return listOrderResponses;
    }

    private static Date convertToDate(LocalDateTime dateTime) {
        return dateTime != null ? Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
}