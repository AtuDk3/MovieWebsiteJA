package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.AdsDTO;
import com.project.MovieWebsite.dtos.MovieDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Ads;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.repositories.AdsRepository;
import com.project.MovieWebsite.responses.AdsListResponse;
import com.project.MovieWebsite.responses.AdsResponse;
import com.project.MovieWebsite.responses.MovieListResponse;
import com.project.MovieWebsite.responses.MovieResponse;
import com.project.MovieWebsite.services.AdsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

@RestController
@RequestMapping("${api.prefix}/ads")
@RequiredArgsConstructor

public class AdsController {
    private final AdsService adsService;
    private final AdsRepository adsRepository;

    @GetMapping("")
    public ResponseEntity<AdsListResponse> getAllAds(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").descending());
        Page<AdsResponse> adsPage = adsService.getAllAds(keyword, pageRequest);
        int totalPages = adsPage.getTotalPages();
        List<AdsResponse> adsList = adsPage.getContent();
        return ResponseEntity.ok(AdsListResponse.builder()
                .adsList(adsList).totalPages(totalPages).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateAds(@PathVariable int id, @Valid @RequestBody AdsDTO adsDTO, BindingResult result) throws Exception {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(Collections.singletonMap("errors", String.join(", ", errorsMessage)));
        }
        adsService.updateAds(id, adsDTO);
        return ResponseEntity.ok(Collections.singletonMap("message", "Update ads successfully!"));
    }

    @PostMapping("")
    public ResponseEntity<?> createAds(@Valid @RequestBody AdsDTO adsDTO, BindingResult result) throws DataNotFoundException {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(Collections.singletonMap("errors", String.join(", ", errorsMessage)));
        }
        adsService.createAds(adsDTO);
        return ResponseEntity.ok(Collections.singletonMap("message", "Create ads successfully!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAds(@PathVariable int id) {
        adsService.deleteAds(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Delete ads successfully!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdsById(
            @PathVariable("id") int adsId
    ){
        try {
            Ads existingAds = adsService.getAdsById(adsId);
            return ResponseEntity.ok(AdsResponse.fromAds(existingAds));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value= "upload_ads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAds (
            @PathVariable("id") int adsId,
            @Valid @ModelAttribute("file") MultipartFile file){

        try {
            Ads existingAds= adsService.getAdsById(adsId);
            if (file != null) {
                if (file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
                }
                String filename = storeFile(file);
                existingAds.setBannerAds(filename);
                adsRepository.save(existingAds);
            }
            return ResponseEntity.ok("Upload Success Banner Ads");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "upload_image_ads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadBannerAdsFromCreate(@Valid @ModelAttribute("file") MultipartFile file) {
        try {
            if (file != null) {
                if (file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
                }
                String filename = storeFile(file);

                // Create a JSON response
                Map<String, String> response = new HashMap<>();
                response.put("filename", filename);

                return ResponseEntity.ok().body(response);
            }
            return ResponseEntity.badRequest().body("Loi upload image from create ads");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName){
        try{
            Path imagePath= Paths.get("uploads/img_ads/"+imageName);
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
        Path uploadDir = Paths.get("uploads/img_ads");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
}
