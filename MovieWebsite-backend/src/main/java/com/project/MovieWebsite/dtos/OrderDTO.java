package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OrderDTO {

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("price")
    private double price;

}
