package com.project.MovieWebsite.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/vnpay")
public class VnpayReturnController {

    @GetMapping("/return")
    public String handleReturn(@RequestParam Map<String, String> params) {
        // Xử lý phản hồi từ VNPay và cập nhật trạng thái đơn hàng
        return "Thanh toán thành công";
    }
}
