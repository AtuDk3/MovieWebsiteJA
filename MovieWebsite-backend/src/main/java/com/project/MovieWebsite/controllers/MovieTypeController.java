package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.MovieTypeDTO;
import com.project.MovieWebsite.models.MovieType;
import com.project.MovieWebsite.services.MovieTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/movie_types")

public class MovieTypeController {

    private final MovieTypeService movieTypeService;


    @GetMapping("")
    public ResponseEntity<List<MovieType>> getAllMovieTypes(
            /*@RequestParam("page") int page,
            @RequestParam("limit") int limit*/
    ) {
        List<MovieType> movieTypes = movieTypeService.getAllMovieType();
        return ResponseEntity.ok(movieTypes);
    }

    @PostMapping("")
    public ResponseEntity<?> createMovieType(@Valid @RequestBody MovieTypeDTO movieTypeDTO, BindingResult result) {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        movieTypeService.createMovieType(movieTypeDTO);
        return ResponseEntity.ok("Create movie type successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGenreById(@PathVariable int id, @Valid @RequestBody MovieTypeDTO movieTypeDTO) {
        movieTypeService.updateMovieType(id, movieTypeDTO);
        return ResponseEntity.ok("Update movie type successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenreById(@PathVariable int id) {
        movieTypeService.deleteMovieType(id);
        return ResponseEntity.ok("Delete movie type successfully!");
    }
}
