package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.CommentDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Comment;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.repositories.CommentRepository;
import com.project.MovieWebsite.repositories.MovieRepository;
import com.project.MovieWebsite.repositories.UserRepository;
import com.project.MovieWebsite.responses.CommentResponse;
import com.project.MovieWebsite.responses.MovieResponse;
import com.project.MovieWebsite.responses.UserResponse;
import com.project.MovieWebsite.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CommentServiceImpl implements CommentService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public Comment createComment(CommentDTO commentDTO) throws DataNotFoundException {
        Movie existingMovie= movieRepository.findById(commentDTO.getMovieId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie with id: "+commentDTO.getMovieId()));

        User existingUser= userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: "+commentDTO.getUserId()));

        Comment newComment= Comment.builder()
                .movie(existingMovie)
                .user(existingUser)
                .description(commentDTO.getDescription())
                .build();
        return commentRepository.save(newComment);
    }

    @Override
    public Comment getComment(int id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    @Override
    public List<Comment> getAllCommentsByMovie(int movieId) throws Exception{
        Movie existingMovie= movieRepository.findById(movieId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie with id: "+movieId));
        return commentRepository.findByMovie(existingMovie);
    }

    @Override
    public Comment updateComment(int id, CommentDTO commentDTO) throws DataNotFoundException {

        return null;
    }

    @Override
    public void deleteComment(int id) {
        Optional<Comment> optionalComment= commentRepository.findById(id);
        if(optionalComment.isPresent()){
            commentRepository.deleteById(id);
        }
    }

    @Override
    public Page<CommentResponse> getAllCommentByMovie(int movieId, PageRequest pageRequest) {
        Page<Comment> commentPage = commentRepository.getAllCommentByMovie(movieId, pageRequest);
        return mapToCommentResponsePage(commentPage);
    }

    private Page<CommentResponse> mapToCommentResponsePage(Page<Comment> commentPage) {
        return commentPage.map(comment -> {
            CommentResponse commentResponse = CommentResponse.builder()
                    .movieId(comment.getMovie().getId())
                    .userResponse(UserResponse.fromUser(comment.getUser()))
                    .description(comment.getDescription())
                    .createAt(convertToDate(comment.getCreateAt()))
                    .build();
            return commentResponse;
        });
    }

    private static Date convertToDate(LocalDateTime dateTime) {
        return dateTime != null ? Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }


}