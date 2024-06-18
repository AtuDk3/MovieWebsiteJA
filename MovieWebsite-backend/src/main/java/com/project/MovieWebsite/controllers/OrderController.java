package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.MovieTypeDTO;
import com.project.MovieWebsite.dtos.OrderDTO;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.responses.MovieListResponse;
import com.project.MovieWebsite.responses.MovieResponse;
import com.project.MovieWebsite.responses.OrderListResponse;
import com.project.MovieWebsite.responses.OrderResponse;
import com.project.MovieWebsite.services.UserService;
import com.project.MovieWebsite.services.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final OrderServiceImpl orderService;
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<?> createOrder (
            @Valid @RequestBody OrderDTO oderDTO,
            BindingResult result,
            @RequestHeader("Authorization") String authorizationHeader){
        try{
            if (result.hasErrors()){
                List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorsMessage);
            }
            String extractedToken= authorizationHeader.substring(7);
            User user= userService.getUserDetailsFromToken(extractedToken);
            if(user.getId()!= oderDTO.getUserId()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            orderService.createOrder(oderDTO);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable int id, @Valid @RequestBody OrderDTO orderDTO) {
        try{
            orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok("Update movie type successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    //Admin
    @GetMapping("/all_orders")
    public ResponseEntity<?> getAllOrder(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int limit
    ){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("orderDate").ascending());
        Page<OrderResponse> orderPage = orderService.getAllOrders(pageRequest);
        int totalPages = orderPage.getTotalPages();
        List<OrderResponse> orders = orderPage.getContent();
        return ResponseEntity.ok(OrderListResponse.builder()
                .orders(orders).totalPages(totalPages).build());
    }

    @GetMapping("/order_by_user")
    public ResponseEntity<?> getAllOrderByUser(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int limit,
                                               @RequestParam(name = "user_id") int userId
    ){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("orderDate").ascending());
        Page<OrderResponse> orderPage = orderService.getAllOrderByUser(userId,pageRequest);
        int totalPages = orderPage.getTotalPages();
        List<OrderResponse> orders = orderPage.getContent();
        return ResponseEntity.ok(OrderListResponse.builder()
                .orders(orders).totalPages(totalPages).build());
    }


}