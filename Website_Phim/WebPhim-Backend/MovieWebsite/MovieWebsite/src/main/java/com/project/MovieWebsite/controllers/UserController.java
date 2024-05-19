package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.dtos.UserLoginDTO;
import com.project.MovieWebsite.services.ClientService;
import com.project.MovieWebsite.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final UserService userService;
    private final ClientService clientService;

    @PostMapping("/register")
    public  ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO,
                                         BindingResult result){
        try{
            if (result.hasErrors()){
                List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorsMessage);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("Password does not match");
            }
            String otpEmail= clientService.create(userDTO);
            //userService.createUser(userDTO);
            return ResponseEntity.ok("Register successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity <String> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO
            ){

        try{
            String token= userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
            return ResponseEntity.ok("Login successfull with token: "+token);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
