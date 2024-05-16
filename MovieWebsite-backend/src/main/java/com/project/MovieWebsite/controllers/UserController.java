package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.GenreDTO;
import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Genre;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUser(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        List<User>users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    @PostMapping("")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) throws DataNotFoundException {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        userService.createUser(userDTO);
        return ResponseEntity.ok("Create user successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable int id, @Valid @RequestBody UserDTO userDTO) throws DataNotFoundException {
        userService.updateUser(id, userDTO);
        return ResponseEntity.ok("Update user successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Delete users successfully!");
    }
}
