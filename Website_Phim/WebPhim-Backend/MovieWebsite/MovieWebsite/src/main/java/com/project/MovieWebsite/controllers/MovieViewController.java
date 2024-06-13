package com.project.MovieWebsite.controllers;


import com.project.MovieWebsite.repositories.ManagerStorageViewRepository;
import com.project.MovieWebsite.responses.MovieViewResponse;
import com.project.MovieWebsite.services.MovieViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/movie_views")

public class MovieViewController {

    private final MovieViewService movieViewService;
    private final ManagerStorageViewRepository managerStorageViewRepository;
    @GetMapping("/top_view_day")
    public ResponseEntity<List<MovieViewResponse>> getTopMoviesByDay() {
        return ResponseEntity.ok(MovieViewResponse.fromMovie(movieViewService.getTopMoviesByDay()));
    }

    @GetMapping("/top_view_week")
    public ResponseEntity<List<MovieViewResponse>> getTopMoviesByWeek() {
        return ResponseEntity.ok(MovieViewResponse.fromMovie(movieViewService.getTopMoviesByWeek()));
    }

    @GetMapping("/top_view_month")
    public ResponseEntity<List<MovieViewResponse>> getTopMoviesByMonth() {
        return ResponseEntity.ok(MovieViewResponse.fromMovie(movieViewService.getTopMoviesByMonth()));
    }

    @PostMapping("")
    public ResponseEntity<?> incrementMovieView(@RequestBody Map<String, Integer> payload) {
        try{
            int movieId = payload.get("movie_id");
            movieViewService.incrementMovieView(movieId);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> updateViewForDay() {
        try{
            movieViewService.updateViewsForDay();
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    //Admin
    @DeleteMapping("/delete_old_view")
    public ResponseEntity<?> deleteOldViewMonth() {
        try{
            movieViewService.deleteOldViewsMonth();
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/last_delete_view")
    public ResponseEntity<?> getLastDelete() {
        try{
            managerStorageViewRepository.getAll();
            return ResponseEntity.ok(managerStorageViewRepository.getAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
