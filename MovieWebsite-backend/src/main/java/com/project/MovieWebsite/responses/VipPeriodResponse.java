package com.project.MovieWebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.models.VipPeriod;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VipPeriodResponse {


    @JsonProperty("registration_date")
    private Date registrationDate;

    @JsonProperty("expiration_date")
    private Date expirationDate;

    public static VipPeriodResponse fromVipPeriod(VipPeriod vipPeriod) {
        VipPeriodResponse vipPeriodResponse = VipPeriodResponse.builder().
                registrationDate(convertToDate(vipPeriod.getRegistrationDate())).
                expirationDate(convertToDate(vipPeriod.getExpirationDate())).
                build();
        return vipPeriodResponse;
    }

    private static Date convertToDate(LocalDateTime dateTime) {
        return dateTime != null ? Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }


}