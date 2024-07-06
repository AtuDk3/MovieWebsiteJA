
package com.project.MovieWebsite.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public abstract class PaymentDTO {
    @Builder
    @Getter
    @Setter
    public static class VNPayResponse {
        private String code;
        private String message;
        private String paymentUrl;
    }
}
