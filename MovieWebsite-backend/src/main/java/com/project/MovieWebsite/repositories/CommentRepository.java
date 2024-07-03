package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Comment;
import com.project.MovieWebsite.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
