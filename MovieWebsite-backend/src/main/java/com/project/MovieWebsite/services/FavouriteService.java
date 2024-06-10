package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.FavouriteDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Favourite;

import java.util.List;

public interface FavouriteService {

    Favourite createFavourite(FavouriteDTO favoriteDTO) throws DataNotFoundException;

    Favourite getFavourite(int id);

    List<Favourite> getFavouritesByUser(int userId) throws DataNotFoundException;

    Favourite updateFavourite(int id, FavouriteDTO favoriteDTO) throws DataNotFoundException;

    void deleteFavourite(int userId, int movieId) throws DataNotFoundException;
}