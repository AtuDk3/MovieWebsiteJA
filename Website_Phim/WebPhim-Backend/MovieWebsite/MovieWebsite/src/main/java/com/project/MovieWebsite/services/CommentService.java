package com.project.MovieWebsite.services;


import com.project.MovieWebsite.dtos.CommentDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(CommentDTO commentDTO) throws DataNotFoundException;

    Comment getComment(int id);

    List<Comment> getAllComments();

    Comment updateComment(int id, CommentDTO commentDTO) throws DataNotFoundException;

    void deleteComment(int id);
}
