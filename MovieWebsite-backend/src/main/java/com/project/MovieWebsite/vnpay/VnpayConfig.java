package com.project.MovieWebsite.vnpay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VnpayConfig {
    @Value("${vnpay.tmnCode}")
    private String vnp_TmnCode;

    @Value("${vnpay.hashSecret}")
    private String vnp_HashSecret;

    @Value("${vnpay.url}")
    private String vnp_Url;

    @Value("${vnpay.returnUrl}")
    private String vnp_Returnurl;

    // Getters
    public String getVnp_TmnCode() { return vnp_TmnCode; }
    public String getVnp_HashSecret() { return vnp_HashSecret; }
    public String getVnp_Url() { return vnp_Url; }
    public String getVnp_Returnurl() { return vnp_Returnurl; }
}
