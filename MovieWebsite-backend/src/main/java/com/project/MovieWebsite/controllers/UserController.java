package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.components.LocalizationUtil;
import com.project.MovieWebsite.constants.MessageKeys;
import com.project.MovieWebsite.dtos.UpdateUserDTO;
import com.project.MovieWebsite.dtos.UserDTO;
import com.project.MovieWebsite.dtos.UserLoginDTO;
import com.project.MovieWebsite.dtos.VipPeriodDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.exceptions.MailErrorExeption;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.models.VipPeriod;
import com.project.MovieWebsite.repositories.UserRepository;
import com.project.MovieWebsite.repositories.VipPeriodRepository;
import com.project.MovieWebsite.responses.LoginResponse;
import com.project.MovieWebsite.responses.UserResponse;
import com.project.MovieWebsite.responses.VipPeriodResponse;
import com.project.MovieWebsite.services.ClientService;
import com.project.MovieWebsite.services.UserService;
import com.project.MovieWebsite.services.VipPeriodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
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
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final LocalizationUtil localizationUtil;
    private final ClientService clientService;
    private final VipPeriodService vipPeriodService;
    private final VipPeriodRepository vipPeriodRepository;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                // Nếu có lỗi trong dữ liệu đầu vào, trả về danh sách các lỗi
                List<String> errors = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errors);
            }
            // Gọi userService để tạo người dùng
            userService.createUser(userDTO);
            // Trả về thông báo thành công
            Map<String, String> response = new HashMap<>();
            response.put("message", "User created successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Nếu có lỗi, trả về thông báo lỗi
            return ResponseEntity.badRequest().body("Failed to create user: " + e.getMessage());
        }
    }

    @PostMapping("/check-register")
    public ResponseEntity<?> checkRegister(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errors = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errors);
            }
            userService.checkAccount(userDTO);
            return ResponseEntity.ok().build();
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("phoneError", e.getMessage()));
        } catch (MailErrorExeption e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("emailError", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create user: " + e.getMessage());
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<Object> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
//        try {
//            String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "Login successful with token: " + token);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Invalid credentials. Please check your phone number and password.");
//            return ResponseEntity.badRequest().body(errorResponse);
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
            return ResponseEntity.ok(
                    LoginResponse.builder()
                            .message(localizationUtil.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                            .token(token)
                            .build());
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("errorActive", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("errorPass", e.getMessage()));
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
                return ResponseEntity.ok(filename);
            }
            return ResponseEntity.ok("Upload Error");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName
    ){
        try{
            Path imagePath= Paths.get("uploads/img_avatar/"+imageName);
            UrlResource resource= new UrlResource(imagePath.toUri());

            if(resource.exists()){
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }else{
                return ResponseEntity.notFound().build();
            }

        }catch (Exception e){
            return ResponseEntity.notFound().build();
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

    @PostMapping("/details")
    public ResponseEntity<UserResponse> getUserDetails(@RequestHeader("Authorization") String authorizationHeader){
        try{
            String extractedToken= authorizationHeader.substring(7);
            User user= userService.getUserDetailsFromToken(extractedToken);
            return ResponseEntity.ok(UserResponse.fromUser(user));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/details/{userId}")
    public ResponseEntity<UserResponse> updateUserDetails(
            @PathVariable int userId,
            @RequestBody UpdateUserDTO userUpdateDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ){
        try{
            String extractedToken= authorizationHeader.substring(7);
            User user= userService.getUserDetailsFromToken(extractedToken);
            if(user.getId()!= userId){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            User updateUser= userService.updateUser(userId, userUpdateDTO);

            return ResponseEntity.ok(UserResponse.fromUser(updateUser));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/checkCurrentPassword")
    public ResponseEntity<?> checkCurrentPassword(@RequestBody Map<String, String> request, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String extractedToken = authorizationHeader.substring(7);
            User user = userService.getUserDetailsFromToken(extractedToken);
            boolean isPasswordValid = userService.checkCurrentPassword(user.getId(), request.get("password"));
            if (isPasswordValid) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(400).body("Incorrect current password.");
            }
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request, @RequestHeader("Authorization") String authorizationHeader) {

        try {
            String extractedToken = authorizationHeader.substring(7);
            User user = userService.getUserDetailsFromToken(extractedToken);
            userService.updatePassword(user.getPhoneNumber(), request.get("password"));
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
        }
        String check_otp= clientService.forgot_password(user);
        Map<String, String> response = new HashMap<>();
        response.put("check_otp", check_otp);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate-account")
    public ResponseEntity<?> authenticateAccount(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String name= request.get("name");
        String check_otp= clientService.authenticate_account(name, email);
        Map<String, String> response = new HashMap<>();
        response.put("check_otp", check_otp);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String newPassword = request.get("newPassword");
        String email = request.get("email");
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
        }
        user= userService.updatePassword(user.getPhoneNumber(), newPassword) ; // Consider encrypting the password
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/vip_period")
    public ResponseEntity<?> createVipPeriod(@Valid @RequestBody VipPeriodDTO vipPeriodDTO, BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        try{
            vipPeriodService.createVipPeriod(vipPeriodDTO);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/vip_period")
    public ResponseEntity<VipPeriodResponse> getVipPeriod(@RequestHeader("Authorization") String authorizationHeader){
        try{
            String extractedToken= authorizationHeader.substring(7);
            User user= userService.getUserDetailsFromToken(extractedToken);
            VipPeriod vipPeriod= vipPeriodRepository.findByUserId(user.getId());
            return ResponseEntity.ok(VipPeriodResponse.fromVipPeriod(vipPeriod));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/vip_period/{userId}")
    public ResponseEntity<?> deleteVipPeriod(@PathVariable int userId,
                                             @RequestHeader("Authorization") String authorizationHeader){
        try{
            String extractedToken= authorizationHeader.substring(7);
            User user= userService.getUserDetailsFromToken(extractedToken);
            if(user.getId()!= userId){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            vipPeriodService.deleteVipPeriod(userId);

            return ResponseEntity.ok().build();

        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }




}