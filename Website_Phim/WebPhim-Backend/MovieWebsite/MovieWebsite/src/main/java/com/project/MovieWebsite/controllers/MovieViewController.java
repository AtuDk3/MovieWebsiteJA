package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.MovieView;
import com.project.MovieWebsite.responses.MovieViewResponse;
import com.project.MovieWebsite.services.MovieViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/movie_views")

public class MovieViewController {

    private final MovieViewService movieViewService;

    @GetMapping("/top_view_day")
    public ResponseEntity<List<MovieViewResponse>> getTopMoviesByDay(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(MovieViewResponse.fromMovie(movieViewService.getTopMoviesByDay(date)));
    }

    @GetMapping("/top_view_week")
    public ResponseEntity<List<MovieViewResponse>> getTopMoviesByWeek(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startOfWeek) {
        return ResponseEntity.ok(MovieViewResponse.fromMovieView(movieViewService.getTopMoviesByWeek(startOfWeek)));
    }

    @GetMapping("/top_view_month")
    public ResponseEntity<List<MovieViewResponse>> getTopMoviesByMonth(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startOfMonth) {
        return ResponseEntity.ok(MovieViewResponse.fromMovieView(movieViewService.getTopMoviesByMonth(startOfMonth)));
    }

    @PostMapping("")
    public ResponseEntity<?> incrementMovieView(@RequestParam(name = "movie_id") int movieId) {
        try{
            movieViewService.incrementMovieView(movieId);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
