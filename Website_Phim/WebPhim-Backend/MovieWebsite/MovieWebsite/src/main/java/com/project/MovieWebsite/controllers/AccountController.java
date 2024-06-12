package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.responses.FavouriteResponse;
import com.project.MovieWebsite.responses.MovieViewResponse;
import com.project.MovieWebsite.responses.UserResponse;
import com.project.MovieWebsite.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/accounts")
public class AccountController {

    private final UserService userService;

    @GetMapping("/list_account")
    public ResponseEntity<List<UserResponse>> getListAccount() {
        List<User> listUser= userService.getAllUser();
        return ResponseEntity.ok(UserResponse.fromListUser(listUser));
    }

    @GetMapping("ban_account")
    public ResponseEntity<?> banAccount(@RequestParam int user_id) {
        try {
            userService.ban_account(user_id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("unban_account")
    public ResponseEntity<?> unBanAccount(@RequestParam int user_id) {
        try {
            userService.unban_account(user_id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
