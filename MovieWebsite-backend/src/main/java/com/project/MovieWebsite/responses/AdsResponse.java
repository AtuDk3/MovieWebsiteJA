package com.project.MovieWebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieWebsite.models.Ads;
import jakarta.persistence.Column;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AdsResponse {
    private int id;

    private String name;

    private String description;

    @JsonProperty("banner_ads")
    private String bannerAds;

    @JsonProperty("create_at")
    private Date createAt;

    @JsonProperty("expiration_at")
    private Date expirationAt;

    private int amount;

    @JsonProperty("is_active")
    private int isActive = 1;

    public static AdsResponse fromAds(Ads ads){
        AdsResponse adsResponse = AdsResponse.builder()
                .id(ads.getId())
                .name(ads.getName())
                .description(ads.getDescription())
                .createAt(ads.getCreateAt())
                .expirationAt(ads.getExpirationAt())
                .amount(ads.getAmount())
                .isActive(ads.getIsActive())
                .build();
        return adsResponse;
    }

    public static List<AdsResponse> fromListAds(List<Ads> listAds){
        List<AdsResponse> listAdsResponse= new ArrayList<>();
        for(Ads ads: listAds) {
            AdsResponse adsResponse = AdsResponse.builder()
                    .id(ads.getId())
                    .name(ads.getName())
                    .description(ads.getDescription())
                    .createAt(ads.getCreateAt())
                    .expirationAt(ads.getExpirationAt())
                    .amount(ads.getAmount())
                    .isActive(ads.getIsActive())
                    .build();
            listAdsResponse.add(adsResponse);
        }
        return listAdsResponse;
    }
}
