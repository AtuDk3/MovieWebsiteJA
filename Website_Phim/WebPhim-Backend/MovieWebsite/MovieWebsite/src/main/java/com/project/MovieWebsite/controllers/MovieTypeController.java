
package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.CountryDTO;
import com.project.MovieWebsite.dtos.MovieTypeDTO;
import com.project.MovieWebsite.models.Country;
import com.project.MovieWebsite.models.MovieType;
import com.project.MovieWebsite.services.MovieTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/movie_types")

public class MovieTypeController {

    private final MovieTypeService movieTypeService;


    @GetMapping("")
    public ResponseEntity<List<MovieType>> getAllMovieTypes() {
        List<MovieType> movieTypes = movieTypeService.getAllMovieType();
        return ResponseEntity.ok(movieTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieTypeById(@PathVariable int id) {
        try {
            MovieType existingMovieType = movieTypeService.getMovieTypeById(id);
            return ResponseEntity.ok(existingMovieType);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateMovieType(@PathVariable int id, @Valid @RequestBody MovieTypeDTO movieTypeDTO, BindingResult result) {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(Collections.singletonMap("errors", String.join(", ", errorsMessage)));
        }
        movieTypeService.updateMovieType(id, movieTypeDTO);
        return ResponseEntity.ok(Collections.singletonMap("message", "Update movie type successfully!"));
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> createMovieType(@Valid @RequestBody MovieTypeDTO movieTypeDTO, BindingResult result) {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(Collections.singletonMap("errors", String.join(", ", errorsMessage)));
        }
        movieTypeService.createMovieType(movieTypeDTO);
        return ResponseEntity.ok(Collections.singletonMap("message", "Create movie type successfully!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteMovieTypeById(@PathVariable int id) {
        movieTypeService.deleteMovieType(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Delete movie type successfully!"));
    }
}
