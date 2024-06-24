package com.project.MovieWebsite.controllers;


import com.project.MovieWebsite.dtos.AdsDTO;
import com.project.MovieWebsite.dtos.CommentDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Ads;
import com.project.MovieWebsite.models.Comment;
import com.project.MovieWebsite.responses.*;
import com.project.MovieWebsite.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDTO commentDTO, BindingResult result) throws DataNotFoundException {

        try {
            if (result.hasErrors()){
                List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(Collections.singletonMap("errors", String.join(", ", errorsMessage)));
            }
            commentService.createComment(commentDTO);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

//    @GetMapping("/{movieId}")
//    public ResponseEntity<?> getCommentsByMovi(
//            @PathVariable("movieId") int movieId
//    ){
//        try {
//            List<Comment> comments= commentService.getAllCommentsByMovie(movieId);
//            return ResponseEntity.ok(CommentResponse.fromListComment(comments));
//        } catch (Exception e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @GetMapping("/{movieId}")
    public ResponseEntity<?> getCommentsByMovie(
                                         @PathVariable("movieId") int movieId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int limit
    ){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createAt").ascending());
        Page<CommentResponse> commentPage = commentService.getAllCommentByMovie(movieId, pageRequest);
        int totalPages = commentPage.getTotalPages();
        List<CommentResponse> comments = commentPage.getContent();
        return ResponseEntity.ok(CommentListResponse.builder()
                .comments(comments).totalPages(totalPages).build());
    }

}
