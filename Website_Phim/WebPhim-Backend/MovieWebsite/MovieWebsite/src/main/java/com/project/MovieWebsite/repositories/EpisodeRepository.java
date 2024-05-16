package com.project.MovieWebsite.repositories;

import com.project.MovieWebsite.models.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Integer> {
}
