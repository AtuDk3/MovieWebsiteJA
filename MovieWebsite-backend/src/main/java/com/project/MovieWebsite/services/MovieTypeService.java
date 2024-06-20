
package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.MovieTypeDTO;
import com.project.MovieWebsite.dtos.OrderDTO;
import com.project.MovieWebsite.models.MovieType;
import com.project.MovieWebsite.models.Order;

import java.util.List;

public interface MovieTypeService {

    MovieType createMovieType(MovieTypeDTO movieTypeDTO);

    MovieType getMovieTypeById(int id);

    List<MovieType> getAllMovieType();

    MovieType updateMovieType(int id, MovieTypeDTO movieTypeDTO);

    void deleteMovieType(int id);
}
