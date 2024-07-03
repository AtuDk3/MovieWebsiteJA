package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.OrderDTO;
import com.project.MovieWebsite.services.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("")
    public ResponseEntity<?> createOrder (
            @Valid @RequestBody OrderDTO oderDTO,
            BindingResult result){
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        try{
            orderService.createOrder(oderDTO);
            return ResponseEntity.ok("ok");
        }catch (Exception e){

        }
        return null;
    }




}
