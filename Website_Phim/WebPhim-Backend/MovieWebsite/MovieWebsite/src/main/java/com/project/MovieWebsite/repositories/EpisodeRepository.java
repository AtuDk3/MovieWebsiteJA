package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Episode;
import com.project.MovieWebsite.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Integer> {
    List<Episode> findByMovieId(int movieId);

    List<Episode> findByMovieAndEpisode(Movie movie, int episode);
}