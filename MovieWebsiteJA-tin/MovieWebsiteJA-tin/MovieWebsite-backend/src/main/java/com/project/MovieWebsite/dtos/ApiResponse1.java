package com.project.MovieWebsite.dtos;

import jakarta.mail.FetchProfile;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse1 {
    private List<Item> items;
}