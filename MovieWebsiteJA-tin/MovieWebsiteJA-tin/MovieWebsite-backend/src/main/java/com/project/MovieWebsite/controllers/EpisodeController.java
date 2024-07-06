package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.EpisodeDTO;
import com.project.MovieWebsite.dtos.FavouriteDTO;
import com.project.MovieWebsite.models.Episode;
import com.project.MovieWebsite.models.Favourite;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.repositories.EpisodeRepository;
import com.project.MovieWebsite.responses.EpisodeResponse;
import com.project.MovieWebsite.services.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    //Admin
    @GetMapping("/list-episode-by-movie/{movieId}")
    public ResponseEntity<List<EpisodeResponse>> getListEpisodesByMovieId(@PathVariable int movieId) {
        return ResponseEntity.ok(EpisodeResponse.fromListEpisode(episodeRepository.findByMovieId(movieId)));
    }

    @PostMapping("")
    public ResponseEntity<?> addEpisode(
            @RequestBody EpisodeDTO episodeDTO
    ){
        try{
            Episode episode= episodeService.createEpisode(episodeDTO);
            Map<String, Integer> response = new HashMap<>();
            response.put("movie_id", episode.getMovie().getId());
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(Collections.singletonMap("existError", e.getMessage()));
        }
    }

    @PutMapping("/update_episode/{id}")
    public ResponseEntity<?> updateEpisode(
            @PathVariable int id,
            @RequestBody EpisodeDTO episodeDTO
    ){
        try{
            Episode episode= episodeService.updateEpisode(id, episodeDTO);
            Map<String, Integer> response = new HashMap<>();
            response.put("movie_id", episode.getMovie().getId());
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(Collections.singletonMap("existError", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEpisode(
            @PathVariable("id") int Id
    ){
        try {
            episodeService.deleteEpisode(Id);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}