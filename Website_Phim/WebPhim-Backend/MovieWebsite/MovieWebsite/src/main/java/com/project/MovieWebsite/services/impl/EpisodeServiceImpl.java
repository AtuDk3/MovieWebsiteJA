
package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.EpisodeDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Episode;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.repositories.EpisodeRepository;
import com.project.MovieWebsite.repositories.MovieRepository;
import com.project.MovieWebsite.services.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EpisodeServiceImpl implements EpisodeService {

    private final MovieRepository movieRepository;
    private final EpisodeRepository episodeRepository;

    @Override
    public Episode createEpisode(EpisodeDTO episodeDTO) throws DataNotFoundException {
        Movie existingMovie= movieRepository.findById(episodeDTO.getMovieId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie type with id: "+episodeDTO.getMovieId()));
        Episode newEpisode= Episode.builder()
                .movie(existingMovie)
                .episode(episodeDTO.getEpisode())
                .movieUrl(episodeDTO.getMovieUrl())
                .build();
        return episodeRepository.save(newEpisode);
    }

    @Override
    public Episode getEpisode(int Id) {
        return episodeRepository.findById(Id).orElseThrow(() -> new RuntimeException("Episode not found"));
    }

    @Override
    public List<Episode> getEpisodeByMovieId(int movieId) {
        List<Episode> episodes = episodeRepository.findByMovieId(movieId);
        if (!episodes.isEmpty()) {
            return episodes;
        } else {
            throw new RuntimeException("Episodes not found for movieId: " + movieId);
        }
    }

    @Override
    public List<Episode> getAllEpisodes() {
        return episodeRepository.findAll();
    }

    @Override
    public Episode updateEpisode(int id, EpisodeDTO episodeDTO) throws DataNotFoundException {
        Episode existingEpisode= getEpisode(id);
        if(existingEpisode!=null){
            Movie existingMovie= movieRepository.findById(episodeDTO.getMovieId())
                    .orElseThrow(() -> new DataNotFoundException("Cannot find movie type with id: "+episodeDTO.getMovieId()));

            existingEpisode.setMovie(existingMovie);
            existingEpisode.setEpisode(episodeDTO.getEpisode());
            existingEpisode.setMovieUrl(episodeDTO.getMovieUrl());

            return episodeRepository.save(existingEpisode);
        }
        return null;
    }

    @Override
    public void deleteEpisode(int id) {
        Optional<Episode> optionalEpisode= episodeRepository.findById(id);
        if(optionalEpisode.isPresent()){
            episodeRepository.deleteById(id);
        }
    }
}
