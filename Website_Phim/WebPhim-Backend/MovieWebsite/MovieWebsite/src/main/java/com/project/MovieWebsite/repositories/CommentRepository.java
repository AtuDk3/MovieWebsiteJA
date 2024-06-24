package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Comment;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByMovie(Movie movie);

    @Query("select cm from Comment cm where"
            + " cm.movie.id = :movieId")
    Page<Comment> getAllCommentByMovie(
            @Param("movieId") int movieId,
            Pageable pageable
    );
}
