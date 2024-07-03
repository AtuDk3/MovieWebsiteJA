package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.CommentDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Comment;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.repositories.CommentRepository;
import com.project.MovieWebsite.repositories.MovieRepository;
import com.project.MovieWebsite.repositories.UserRepository;
import com.project.MovieWebsite.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
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
}
