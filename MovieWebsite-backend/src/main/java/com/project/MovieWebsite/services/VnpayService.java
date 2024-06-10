package com.project.MovieWebsite.services;

import com.project.MovieWebsite.vnpay.VnpayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VnpayService {

    @Autowired
    private VnpayConfig vnpayConfig;

    public String createPaymentUrl(String orderId, String amount) {
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnpayConfig.getVnp_TmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(Integer.parseInt(amount) * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", orderId);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + orderId);
        vnp_Params.put("vnp_ReturnUrl", vnpayConfig.getVnp_Returnurl());
        vnp_Params.put("vnp_IpAddr", "127.0.0.1"); // Replace with client's IP

        String query = vnp_Params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((entry1, entry2) -> entry1 + "&" + entry2)
                .orElse("");

        String hashData = vnpayConfig.getVnp_HashSecret() + query;
        String secureHash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(hashData);

        return vnpayConfig.getVnp_Url() + "?" + query + "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + secureHash;
    }
}

