package com.project.MovieWebsite.services;

import com.project.MovieWebsite.configurations.VNPAYConfig;
import com.project.MovieWebsite.dtos.PaymentDTO;
import com.project.MovieWebsite.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final VNPAYConfig vnPayConfig;

    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        // Tạo queryUrl
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, false);

        // Tạo chữ ký và thêm vào queryUrl
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), queryUrl);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

        // Tạo paymentUrl từ queryUrl
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        // Trả về đối tượng VNPayResponse
        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl)
                .build();
    }
}
