package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.GenreDTO;
import com.project.MovieWebsite.dtos.UserVIPDTO;
import com.project.MovieWebsite.models.Genre;
import com.project.MovieWebsite.models.UserVIP;
import com.project.MovieWebsite.services.GenreService;
import com.project.MovieWebsite.services.UserVIPService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users_vip")
@RequiredArgsConstructor

public class UserVIPController {
    private final UserVIPService userVIPService;

    @GetMapping("")
    public ResponseEntity<List<UserVIP>> getAllUserVIP(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        List<UserVIP> userVIP = userVIPService.getAllUserVIP();
        return ResponseEntity.ok(userVIP);
    }

    @PostMapping("")
    public ResponseEntity<?> createUserVIP(@Valid @RequestBody UserVIPDTO userVIPDTO, BindingResult result) {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        userVIPService.createUserVIP(userVIPDTO);
        return ResponseEntity.ok("Create user VIP successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserVIPById(@PathVariable int id, @Valid @RequestBody UserVIPDTO userVIPDTO) {
        userVIPService.updateUserVIP(id, userVIPDTO);
        return ResponseEntity.ok("Update user VIP successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserVIPById(@PathVariable int id) {
        userVIPService.deleteUserVIP(id);
        return ResponseEntity.ok("Delete user VIP successfully!");
    }
}
