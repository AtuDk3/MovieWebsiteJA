package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.VipPeriodDTO;
import com.project.MovieWebsite.models.User;
import com.project.MovieWebsite.models.VipPeriod;
import com.project.MovieWebsite.repositories.VipPeriodRepository;
import com.project.MovieWebsite.responses.UserResponse;
import com.project.MovieWebsite.responses.VipPeriodResponse;
import com.project.MovieWebsite.services.UserService;
import com.project.MovieWebsite.services.VipPeriodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/vip_periods")
public class VipPeriodController {

    private final VipPeriodService vipPeriodService;
    private final UserService userService;
    private final VipPeriodRepository vipPeriodRepository;

    @PostMapping("")
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

    @GetMapping("")
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
}
