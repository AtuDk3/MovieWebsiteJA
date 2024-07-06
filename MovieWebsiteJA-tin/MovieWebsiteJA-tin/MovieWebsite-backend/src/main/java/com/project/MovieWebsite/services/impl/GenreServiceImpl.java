
package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.GenreDTO;
import com.project.MovieWebsite.models.Genre;
import com.project.MovieWebsite.repositories.GenreRepository;
import com.project.MovieWebsite.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public Genre createGenre(GenreDTO genre) {
        Genre newGenre = Genre.builder().name(genre.getName()).description(genre.getDescription()).slug(genre.getSlug()).isActive(genre.getIsActive()).build();
        return genreRepository.save(newGenre);
    }

    @Override
    public Genre getGenreById(int genreId) {
        return genreRepository.findById(genreId).orElseThrow(() -> new RuntimeException("Genre not found!"));
    }

    @Override
    public List<Genre> getAllGenre() {
        return genreRepository.findAll();
    }

    @Override
    public Genre updateGenre(int genreId, GenreDTO genre) {
        Genre existsGenre = getGenreById(genreId);
        existsGenre.setName(genre.getName());
        existsGenre.setDescription(genre.getDescription());
        existsGenre.setSlug(genre.getSlug());
        existsGenre.setIsActive(genre.getIsActive());
        genreRepository.save(existsGenre);
        return existsGenre;
    }

    @Override
    public void deleteGenre(int genreId) {
        genreRepository.deleteById(genreId);
    }

}