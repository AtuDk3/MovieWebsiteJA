
package com.project.MovieWebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieWebsite.models.Ads;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @JsonProperty("trading_code")
    private String tradingCode;

    private String email;

    @JsonProperty("create_at")
    private Date createAt;

    @JsonProperty("expiration_at")
    private Date expirationAt;

    private double amount;

    @JsonProperty("is_active")
    private int isActive;

    @JsonProperty("is_confirm")
    private int isConfirm;

    @JsonProperty("number_days")
    private int numberDays;

    @JsonProperty("list_img")
    private List<String> listImg;



    public static AdsResponse fromAds(Ads ads){
//        List<String> lists= new ArrayList<>();
//        List<AdsImage> listAds= adsImageRepository.findByAds(ads);
//        for (AdsImage adsImg: listAds){
//            lists.add(adsImg.getImageUrl());
//        }
        AdsResponse adsResponse = AdsResponse.builder()
                .id(ads.getId())
                .name(ads.getName())
                .description(ads.getDescription())
                .tradingCode(ads.getTradingCode())
                .email(ads.getEmail())
                .amount(ads.getAmount())
                .createAt(convertToDate(ads.getCreateAt()))
                .expirationAt(convertToDate(ads.getExpirationAt()))
                .tradingCode(ads.getTradingCode())
                .numberDays(ads.getNumberDays())
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
                    //.createAt(ads.getCreateAt())
                    //.expirationAt(ads.getExpirationAt())
                    .amount(ads.getAmount())
                    .isActive(ads.getIsActive())
                    .build();
            listAdsResponse.add(adsResponse);
        }
        return listAdsResponse;
    }

    private static Date convertToDate(LocalDateTime dateTime) {
        return dateTime != null ? Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
}