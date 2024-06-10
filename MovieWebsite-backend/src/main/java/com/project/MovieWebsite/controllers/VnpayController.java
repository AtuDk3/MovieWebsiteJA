package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.services.VnpayService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/vnpay")
public class VnpayController {

    @Autowired
    private VnpayService vnpayService;

    @PostMapping("/create-payment")
    public String createPayment(@RequestParam String orderId, @RequestParam String amount) {
        return vnpayService.createPaymentUrl(orderId, amount);
    }
}

