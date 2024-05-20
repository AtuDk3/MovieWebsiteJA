package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.CommentDTO;
import com.project.MovieWebsite.dtos.FavoriteDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Favourite;

import java.util.List;

public interface FavouriteService {

    Favourite createFavourite(FavoriteDTO favoriteDTO) throws DataNotFoundException;

    Favourite getFavourite(int id);

    List<Favourite> getAllFavourites();

    Favourite updateFavourite(int id, FavoriteDTO favoriteDTO) throws DataNotFoundException;

    void deleteFavourite(int id);
}
