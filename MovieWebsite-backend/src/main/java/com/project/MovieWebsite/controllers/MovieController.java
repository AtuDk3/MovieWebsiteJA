package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.MovieDTO;
import com.project.MovieWebsite.dtos.MovieTypeDTO;
import com.project.MovieWebsite.services.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/movies")
public class MovieController {

    private final MovieService movieService;

    @PostMapping("")
    public ResponseEntity<?> createMovieType(@Valid @RequestBody MovieDTO movieDTO, BindingResult result) {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        try{
            movieService.createMovie(movieDTO);
            return ResponseEntity.ok("Create movie type successfully!");
        }catch (Exception e){

        }
        return null;

    }
}
