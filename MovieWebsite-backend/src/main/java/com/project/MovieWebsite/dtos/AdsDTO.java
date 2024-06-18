package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AdsDTO {
    @NotBlank(message = "Name ads required")
    private String name;

    @NotBlank(message = "Description ads required")
    private String description;

    @NotBlank(message = "Banner ads required")
    private String bannerAds;

    @JsonProperty("create_at")
    private Date createAt;

    @JsonProperty("expiration_at")
    private Date expirationAt;

    private int amount;

    @JsonProperty("is_active")
    private int isActive=1;

}
