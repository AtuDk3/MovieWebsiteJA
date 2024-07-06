package com.project.MovieWebsite.services;


import com.project.MovieWebsite.dtos.CommentDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Comment;
import com.project.MovieWebsite.responses.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CommentService {

    Comment createComment(CommentDTO commentDTO) throws DataNotFoundException;

    Comment getComment(int id);

    List<Comment> getAllCommentsByMovie(int movieId) throws Exception;

    Comment updateComment(int id, CommentDTO commentDTO) throws DataNotFoundException;

    void deleteComment(int id);

    Page<CommentResponse> getAllCommentByMovie(int movieId, PageRequest pageRequest);
}