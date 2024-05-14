package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.GenreDTO;
import com.project.MovieWebsite.models.Genre;

import java.util.List;

public interface GenreService {
    Genre createGenre(GenreDTO genre);

    Genre getGenreById(int genreId);

    List<Genre> getAllGenre();

    Genre updateGenre(int genreId, GenreDTO genre);

    void deleteGenre(int genreId);

}
