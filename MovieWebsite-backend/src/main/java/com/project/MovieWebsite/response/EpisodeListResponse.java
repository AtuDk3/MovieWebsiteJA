package com.project.MovieWebsite.response;

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
