package com.project.MovieWebsite.responses;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EpisodeListResponse {
    private List<EpisodeResponse> episodes;
}
