package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.FavouriteDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Favourite;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.repositories.FavouriteRepository;
import com.project.MovieWebsite.repositories.MovieRepository;
import com.project.MovieWebsite.repositories.UserRepository;
import com.project.MovieWebsite.services.FavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class FavouriteServiceImpl implements FavouriteService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final FavouriteRepository favouriteRepository;

    @Override
    public Favourite createFavourite(FavouriteDTO favouriteDTO) throws DataNotFoundException {

        Movie existingMovie= movieRepository.findById(favouriteDTO.getMovieId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie with id: "+favouriteDTO.getMovieId()));

        User existingUser= userRepository.findById(favouriteDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: "+favouriteDTO.getUserId()));

        List<Favourite> lists= getFavouritesByUser(favouriteDTO.getUserId());
        for(Favourite favourite: lists){
            if(favourite.getMovie().getId()==favouriteDTO.getMovieId()){
                throw new DataNotFoundException("Đã có trong mục phim yêu thích!");
            }
        }
        Favourite newFavourite = Favourite.builder()
                .movie(existingMovie)
                .user(existingUser)
                .build();
        return favouriteRepository.save(newFavourite);
    }

    @Override
    public Favourite getFavourite(int id) {
        return favouriteRepository.findById(id).orElseThrow(() -> new RuntimeException("Favourite not found"));
    }

    @Override
    public List<Favourite> getFavouritesByUser(int userId) throws DataNotFoundException{
        Optional<List<Favourite>> listFavourites= favouriteRepository.findByUserId(userId);
        if (listFavourites.isPresent()){
            return listFavourites.get();
        }else{
            throw new DataNotFoundException("không có phim yêu thích");
        }
    }

    @Override
    public Favourite updateFavourite(int id, FavouriteDTO favoriteDTO) throws DataNotFoundException {
        Favourite existingFavourite= getFavourite(id);
        if(existingFavourite!=null){
            Movie existingMovie= movieRepository.findById(favoriteDTO.getMovieId())
                    .orElseThrow(() -> new DataNotFoundException("Cannot find movie with id: "+favoriteDTO.getMovieId()));

            User existingUser= userRepository.findById(favoriteDTO.getUserId())
                    .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: "+favoriteDTO.getUserId()));

            existingFavourite.setMovie(existingMovie);
            existingFavourite.setUser(existingUser);

            return  favouriteRepository.save(existingFavourite);
        }
        return null;
    }

    @Override
    public void deleteFavourite(int userId, int movieId) throws DataNotFoundException{
        try {
            List<Favourite> lists = getFavouritesByUser(userId);
            if(!lists.isEmpty()) {
                for (Favourite favourite : lists) {
                    if (favourite.getMovie().getId() == movieId) {
                        favouriteRepository.delete(favourite);
                        return;
                    }
                }
            }
        }catch (Exception e){
            throw new DataNotFoundException(e.getMessage());
        }
    }
}