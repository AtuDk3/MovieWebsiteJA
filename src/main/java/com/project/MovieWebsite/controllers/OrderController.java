package com.project.MovieWebsite.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

//    @GetMapping
//    public List<Order> getAllOrders() {
//        return orderRepository.findAll();
//    }

    @GetMapping("")
    public ResponseEntity<String> getOrderById() {

        return ResponseEntity.ok("haha");
    }
//
//    @PostMapping
//    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
//        Order createdOrder = orderRepository.save(order);
//        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
//        Optional<Order> existingOrderOptional = orderRepository.findById(id);
//        if (existingOrderOptional.isPresent()) {
//            updatedOrder.setId(id);
//            Order savedOrder = orderRepository.save(updatedOrder);
//            return new ResponseEntity<>(savedOrder, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
//        Optional<Order> orderOptional = orderRepository.findById(id);
//        if (orderOptional.isPresent()) {
//            orderRepository.deleteById(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

}
