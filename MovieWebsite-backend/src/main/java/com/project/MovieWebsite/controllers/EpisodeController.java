package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.models.Episode;
import com.project.MovieWebsite.models.Genre;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.repositories.EpisodeRepository;
import com.project.MovieWebsite.response.EpisodeListResponse;
import com.project.MovieWebsite.response.EpisodeResponse;
import com.project.MovieWebsite.response.MovieListResponse;
import com.project.MovieWebsite.response.MovieResponse;
import com.project.MovieWebsite.services.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/episodes")
public class EpisodeController {
    private final EpisodeService episodeService;
    private final EpisodeRepository episodeRepository;

    @GetMapping("")
    public ResponseEntity<List<Episode>> getAllEpisodes(
    ) {
        List<Episode> episodes = episodeService.getAllEpisodes();
        return ResponseEntity.ok(episodes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEpisode(
            @PathVariable("id") int Id
    ){
        try {
            Episode existingEpisode = episodeService.getEpisode(Id);
            return ResponseEntity.ok(existingEpisode);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/movies/{movieId}")
    public List<Episode> getEpisodesByMovieId(@PathVariable int movieId) {
        return episodeRepository.findByMovieId(movieId);
    }


}
