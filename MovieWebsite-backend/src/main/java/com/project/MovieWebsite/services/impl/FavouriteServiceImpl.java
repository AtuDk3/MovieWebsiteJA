package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.FavoriteDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Favourite;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.models.Rate;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.repositories.FavouriteRepository;
import com.project.MovieWebsite.repositories.MovieRepository;
import com.project.MovieWebsite.repositories.UserRepository;
import com.project.MovieWebsite.services.FavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class FavouriteServiceImpl implements FavouriteService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final FavouriteRepository favouriteRepository;

    @Override
    public Favourite createFavourite(FavoriteDTO favoriteDTO) throws DataNotFoundException {
        Movie existingMovie= movieRepository.findById(favoriteDTO.getMovieId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find movie with id: "+favoriteDTO.getMovieId()));

        User existingUser= userRepository.findById(favoriteDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: "+favoriteDTO.getUserId()));

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
    public List<Favourite> getAllFavourites() {
        return favouriteRepository.findAll();
    }

    @Override
    public Favourite updateFavourite(int id, FavoriteDTO favoriteDTO) throws DataNotFoundException {
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
    public void deleteFavourite(int id) {
        Optional<Favourite> optionalFavourite= favouriteRepository.findById(id);
        if(optionalFavourite.isPresent()){
            favouriteRepository.deleteById(id);
        }
    }
}
