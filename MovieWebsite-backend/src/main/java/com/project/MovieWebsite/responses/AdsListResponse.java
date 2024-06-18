package com.project.MovieWebsite.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AdsListResponse {
    private List<AdsResponse> adsList;
    private int totalPages;
}
