package com.project.MovieWebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieWebsite.models.Country;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CountryResponse {

    private int id;

    private String name;

    @JsonProperty("is_active")
    private int isActive;

    public static CountryResponse fromCountry(Country country){
        CountryResponse countryResponse = CountryResponse.builder()
                .id(country.getId())
                .name(country.getName())
                .isActive(country.getIsActive())
                .build();
        return countryResponse;
    }

    public static List<CountryResponse> fromListCountry(List<Country> listCountry){
        List<CountryResponse> listCountryResponse= new ArrayList<>();
        for(Country country: listCountry) {
            CountryResponse countryResponse = CountryResponse.builder()
                    .id(country.getId())
                    .name(country.getName())
                    .isActive(country.getIsActive())
                    .build();
            listCountryResponse.add(countryResponse);
        }
        return listCountryResponse;
    }
}