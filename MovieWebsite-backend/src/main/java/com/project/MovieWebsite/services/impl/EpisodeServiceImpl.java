
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
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie with id: "+episodeDTO.getMovieId()));
        if(episodeRepository.findByMovieAndEpisode(existingMovie, episodeDTO.getEpisode()).size()!=0){
            throw new DataNotFoundException("Episode existed");
        }

        Episode newEpisode= Episode.builder()
                .movie(existingMovie)
                .episode(episodeDTO.getEpisode())
                .movieUrl(episodeDTO.getMovieUrl())
                .build();
        episodeRepository.save(newEpisode);

        List<Episode> listEpisode= episodeRepository.findByMovieId(episodeDTO.getMovieId());
        existingMovie.setEpisode(listEpisode.size());
        movieRepository.save(existingMovie);

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
            if(existingEpisode.getEpisode()!=episodeDTO.getEpisode()){
                existingEpisode.setEpisode(episodeDTO.getEpisode());
            }
            if(!episodeDTO.getMovieUrl().isEmpty()) {
                existingEpisode.setMovieUrl(episodeDTO.getMovieUrl());
                return episodeRepository.save(existingEpisode);
            }
        }
        return existingEpisode;
    }

    @Override
    public void deleteEpisode(int id) throws DataNotFoundException{
        Optional<Episode> optionalEpisode= episodeRepository.findById(id);
        if(optionalEpisode.isPresent()){
            episodeRepository.deleteById(id);
            List<Episode> listEpisode= episodeRepository.findByMovieId(optionalEpisode.get().getMovie().getId());
            Movie existingMovie= movieRepository.findById(optionalEpisode.get().getMovie().getId())
                    .orElseThrow(() -> new DataNotFoundException("Cannot find movie with id"));
            existingMovie.setEpisode(listEpisode.size());
            movieRepository.save(existingMovie);
        }
    }
}
