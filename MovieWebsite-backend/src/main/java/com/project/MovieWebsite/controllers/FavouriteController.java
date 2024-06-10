package com.project.MovieWebsite.controllers;


import com.project.MovieWebsite.dtos.FavouriteDTO;
import com.project.MovieWebsite.models.Favourite;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.responses.FavouriteResponse;
import com.project.MovieWebsite.services.FavouriteService;
import com.project.MovieWebsite.services.UserService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/favourites")
public class FavouriteController {

    private final UserService userService;
    private final FavouriteService favouriteService;

    @PostMapping("")
    public ResponseEntity<?> addFavourite(
            @RequestBody FavouriteDTO favouriteDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ){
        try{
            String extractedToken= authorizationHeader.substring(7);
            User user= userService.getUserDetailsFromToken(extractedToken);
            if(user.getId()!= favouriteDTO.getUserId()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            Favourite favourite= favouriteService.createFavourite(favouriteDTO);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Đã thêm vào mục phim yêu thích!");
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(Collections.singletonMap("existError", e.getMessage()));
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<FavouriteResponse>> getMovieByUserId(
            @PathVariable("user_id") int userId,
            @RequestHeader("Authorization") String authorizationHeader
    ){
        try {
            String extractedToken= authorizationHeader.substring(7);
            User user= userService.getUserDetailsFromToken(extractedToken);
            if(user.getId()!= userId){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            List<Favourite> favourites = favouriteService.getFavouritesByUser(userId);
            return ResponseEntity.ok(FavouriteResponse.fromFavourite(favourites));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{user_id}/count")
    public ResponseEntity<?> getMovieCount(
            @PathVariable("user_id") int userId
    ){
        try {
            List<Favourite> favourites = favouriteService.getFavouritesByUser(userId);
            return ResponseEntity.ok(favourites.size());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{user_id}")
    public ResponseEntity<?> deleteFavourite(@PathVariable("user_id") int userId,
                                             @RequestBody Map<String, Integer> request,
                                             @RequestHeader("Authorization") String authorizationHeader){
        try{
            String extractedToken= authorizationHeader.substring(7);
            User user= userService.getUserDetailsFromToken(extractedToken);
            favouriteService.deleteFavourite(userId,request.get("movie_id"));
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}