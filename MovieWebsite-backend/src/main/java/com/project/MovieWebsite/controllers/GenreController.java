package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.GenreDTO;
import com.project.MovieWebsite.models.Genre;
import com.project.MovieWebsite.services.GenreService;
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
@RequestMapping("${api.prefix}/genres")
@RequiredArgsConstructor

public class GenreController {
    private final GenreService genreService;

    @GetMapping("")
    public ResponseEntity<List<Genre>> getAllGenres(
    ) {
        List<Genre> genres = genreService.getAllGenre();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGenreById(@PathVariable int id) {
        try {
            Genre existingGenre = genreService.getGenreById(id);
            return ResponseEntity.ok(existingGenre);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateGenre(@PathVariable int id, @Valid @RequestBody GenreDTO genreDTO, BindingResult result) {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(Collections.singletonMap("errors", String.join(", ", errorsMessage)));
        }
        genreService.updateGenre(id, genreDTO);
        return ResponseEntity.ok(Collections.singletonMap("message", "Update genre successfully!"));
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> createGenre(@Valid @RequestBody GenreDTO genreDTO, BindingResult result) {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(Collections.singletonMap("errors", String.join(", ", errorsMessage)));
        }
        genreService.createGenre(genreDTO);
        return ResponseEntity.ok(Collections.singletonMap("message", "Create genre successfully!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteGenreById(@PathVariable int id) {
        genreService.deleteGenre(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Delete genre successfully!"));
    }

}