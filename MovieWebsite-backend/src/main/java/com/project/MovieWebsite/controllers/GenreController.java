package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.GenreDTO;
import com.project.MovieWebsite.models.Genre;
import com.project.MovieWebsite.responses.GenreResponse;
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
    //
    // Injecting GenreService dependency
    private final GenreService genreService;

    /**
     * GET /genres
     * Fetches all genres.
     * @return ResponseEntity containing a list of GenreResponse
     */
    @GetMapping("")
    public ResponseEntity<List<GenreResponse>> getAllGenres() {
        List<Genre> genres = genreService.getAllGenre();
        return ResponseEntity.ok(GenreResponse.fromListGenre(genres));
    }

    /**
     * GET /genres/{id}
     * Fetches a genre by its ID.
     * @param id the ID of the genre
     * @return ResponseEntity containing the GenreResponse or an error message
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getGenreById(@PathVariable int id) {
        try {
            Genre existingGenre = genreService.getGenreById(id);
            return ResponseEntity.ok(GenreResponse.fromGenre(existingGenre));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * PUT /genres/{id}
     * Updates an existing genre by its ID.
     * @param id the ID of the genre
     * @param genreDTO the data transfer object containing genre details
     * @param result binding result for validation
     * @return ResponseEntity containing success or error message
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateGenre(@PathVariable int id, @Valid @RequestBody GenreDTO genreDTO, BindingResult result) {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(Collections.singletonMap("errors", String.join(", ", errorsMessage)));
        }
        genreService.updateGenre(id, genreDTO);
        return ResponseEntity.ok(Collections.singletonMap("message", "Update genre successfully!"));
    }

    /**
     * POST /genres
     * Creates a new genre.
     * @param genreDTO the data transfer object containing genre details
     * @param result binding result for validation
     * @return ResponseEntity containing success or error message
     */
    @PostMapping("")
    public ResponseEntity<Map<String, String>> createGenre(@Valid @RequestBody GenreDTO genreDTO, BindingResult result) {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(Collections.singletonMap("errors", String.join(", ", errorsMessage)));
        }
        genreService.createGenre(genreDTO);
        return ResponseEntity.ok(Collections.singletonMap("message", "Create genre successfully!"));
    }

    /**
     * DELETE /genres/{id}
     * Deletes a genre by its ID.
     * @param id the ID of the genre
     * @return ResponseEntity containing success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteGenreById(@PathVariable int id) {
        genreService.deleteGenre(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Delete genre successfully!"));
    }
}
