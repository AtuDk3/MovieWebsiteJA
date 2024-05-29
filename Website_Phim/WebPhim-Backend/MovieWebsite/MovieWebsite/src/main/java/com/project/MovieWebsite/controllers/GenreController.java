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

import java.util.List;

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

    @PostMapping("")
    public ResponseEntity<?> createGenre(@Valid @RequestBody GenreDTO genreDTO, BindingResult result) {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        genreService.createGenre(genreDTO);
        return ResponseEntity.ok("Create genre successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGenreById(@PathVariable int id, @Valid @RequestBody GenreDTO genreDTO) {
        genreService.updateGenre(id, genreDTO);
        return ResponseEntity.ok("Update genre successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenreById(@PathVariable int id) {
        genreService.deleteGenre(id);
        return ResponseEntity.ok("Delete genre successfully!");
    }
}