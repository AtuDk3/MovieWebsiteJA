package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.services.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/payments")
@RequiredArgsConstructor
public class VNPAYController {
    @Autowired
    private VNPAYService vnPayService;

    // Chuyển hướng người dùng đến cổng thanh toán VNPAY
    @PostMapping("/create_order")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, orderTotal, orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    // Sau khi hoàn tất thanh toán, VNPAY sẽ chuyển hướng trình duyệt về URL này
    @GetMapping("/vnpay_payment_return")
    public String paymentCompleted(HttpServletRequest request) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        String frontendUrl = "http://localhost:4200/thanks";  // URL của Angular
        if (paymentStatus == 1) {
            frontendUrl += "?status=success&orderId=" + orderInfo + "&totalPrice=" + totalPrice + "&paymentTime=" + paymentTime + "&transactionId=" + transactionId;
        } else {
            frontendUrl += "?status=fail";
        }

        return "redirect:" + frontendUrl;
    }
}
