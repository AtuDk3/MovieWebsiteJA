package com.project.MovieWebsite.controllers;


import com.project.MovieWebsite.dtos.RateDTO;
import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.models.Favourite;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.repositories.ManagerStorageRateRepository;
import com.project.MovieWebsite.responses.FavouriteResponse;
import com.project.MovieWebsite.services.RateService;
import com.project.MovieWebsite.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/rates")
public class RateController {

    private final RateService rateService;
    private final UserService userService;
    private final ManagerStorageRateRepository managerStorageRateRepository;
    @PostMapping("")
    public ResponseEntity<?> createRate(@Valid @RequestBody RateDTO rateDTO, BindingResult result,
                                        @RequestHeader("Authorization") String authorizationHeader) {
        try {
            if (result.hasErrors()) {
                // Nếu có lỗi trong dữ liệu đầu vào, trả về danh sách các lỗi
                List<String> errors = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errors);
            }
            String extractedToken= authorizationHeader.substring(7);
            User user= userService.getUserDetailsFromToken(extractedToken);
            if(user.getId()!= rateDTO.getUserId()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            // Gọi userService để tạo người dùng
            rateService.createRate(rateDTO);
            rateService.getNumberRatesAndStarOfMovie(rateDTO.getMovieId());
            // Trả về thông báo thành công
            Map<String, String> response = new HashMap<>();
            response.put("message", "Rate created successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Nếu có lỗi, trả về thông báo lỗi
            return ResponseEntity.badRequest().body("Failed to create rate: " + e.getMessage());
        }
    }

    @GetMapping("/{movie_id}/count_average")
    public ResponseEntity<?> getMovieCountAndAverage(
            @PathVariable("movie_id") int movieId
    ){
        try {
            return ResponseEntity.ok(rateService.getNumberRatesAndStarOfMovie(movieId));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    //admin
    @DeleteMapping("/delete_old_rate")
    public ResponseEntity<?> deleteRateOldMonth(){
        try {
            rateService.deleteRateMonth();
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/last_delete_rate")
    public ResponseEntity<?> getLastDelete() {
        try{
            managerStorageRateRepository.getAll();
            return ResponseEntity.ok(managerStorageRateRepository.getAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
