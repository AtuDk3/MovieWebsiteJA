package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.GenreDTO;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/genres")
public class GenreController {
    @GetMapping("")
    public ResponseEntity<String> getAllGenres(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok("OK");
    }

    @PostMapping("")
    public ResponseEntity<?> addGenre(@RequestBody GenreDTO genreDTO, BindingResult result) {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGenreById(@PathVariable int id) {
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenreById(@PathVariable int id) {
        return ResponseEntity.ok("ok");
    }
}
