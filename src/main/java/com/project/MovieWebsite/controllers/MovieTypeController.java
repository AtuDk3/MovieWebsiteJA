package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.MovieTypeDTO;
import com.project.MovieWebsite.services.MovieTypeService;
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
@RequestMapping("${api.prefix}/movie_types")
@RequiredArgsConstructor

public class MovieTypeController {

    private final MovieTypeService movieTypeService;

    @PostMapping("")
    public ResponseEntity<?> createMovieType(
            @Valid @RequestBody MovieTypeDTO movieTypeDTO,
            BindingResult result){
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        movieTypeService.createMovieType(movieTypeDTO);
        return ResponseEntity.ok("ok");
    }
}