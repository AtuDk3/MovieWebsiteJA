package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListEpisodeDTOFromAPI {

    @JsonProperty("server_data")
    private List<EpisodeDTOFromAPI> episodeDTOList;

}