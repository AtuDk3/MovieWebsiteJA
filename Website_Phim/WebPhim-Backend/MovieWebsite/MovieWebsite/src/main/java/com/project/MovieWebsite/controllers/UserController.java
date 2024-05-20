package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.dtos.UserLoginDTO;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.repositories.UserRepository;
import com.project.MovieWebsite.services.ClientService;
import com.project.MovieWebsite.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
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

    @PostMapping(value = "upload_avatar/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAvatar (
            @PathVariable("id") int userId,
            @Valid @ModelAttribute("file") MultipartFile file){

        try {
            User existingUser= userService.getUserById(userId);
            if (file != null) {
                if (file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
                }
                String filename = storeFile(file);
                existingUser.setImgAvatar(filename);
                userRepository.save(existingUser);
            }
            return ResponseEntity.ok("Upload Success Avatar");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        Path uploadDir = Paths.get("uploads/img_avatar");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    @PostMapping("/login")
    public ResponseEntity <String> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO
            ){

        try{
            String token= userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
            return ResponseEntity.badRequest()
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
