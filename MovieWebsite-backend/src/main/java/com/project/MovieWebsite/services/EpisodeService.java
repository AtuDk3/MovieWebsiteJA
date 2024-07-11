package com.project.MovieWebsite.services;


import com.project.MovieWebsite.dtos.EpisodeDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Episode;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface EpisodeService {

    Episode createEpisode(EpisodeDTO episodeDTO) throws DataNotFoundException;

    Episode getEpisode(int Id);

    List<Episode> getEpisodeByMovieId(int movieId);

    List<Episode> getAllEpisodes();

    Episode updateEpisode(int id, EpisodeDTO episodeDTO) throws DataNotFoundException;

    void deleteEpisode(int id);
///////////////
}
