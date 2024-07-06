package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.SendDTO;
import com.project.MovieWebsite.services.UserService;
import com.project.MovieWebsite.services.impl.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${api.prefix}/auths")
@RequiredArgsConstructor
public class AuthController {

    private OTPService otpService= new OTPService();

    private UserService userService;  // Assume this service handles user-related operations

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String phoneNumber) {
        try {
            otpService.generateOtp(phoneNumber);
            return ResponseEntity.ok("OTP đã được gửi đến số điện thoại của bạn.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending OTP: " + e.getMessage());
        }
    }

    // Endpoint để xác thực OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam("phone_number")  String phoneNumber, @RequestParam("otp") String otp) {
        if (otpService.validateOtp(phoneNumber, otp)) {
            return ResponseEntity.ok("OTP hợp lệ. Bạn có thể đặt lại mật khẩu.");
        } else {
            return ResponseEntity.status(400).body("OTP không hợp lệ.");
        }
    }

}
