
package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.AdsDTO;
import com.project.MovieWebsite.exceptions.DataNotFoundException;
import com.project.MovieWebsite.models.Ads;
import com.project.MovieWebsite.models.AdsImage;
import com.project.MovieWebsite.repositories.AdsImageRepository;
import com.project.MovieWebsite.repositories.AdsRepository;
import com.project.MovieWebsite.responses.AdsListResponse;
import com.project.MovieWebsite.responses.AdsResponse;
import com.project.MovieWebsite.services.AdsService;
import com.project.MovieWebsite.services.ClientService;
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
    private final ClientService clientService;
    private final AdsImageRepository adsImageRepository;

    @GetMapping("/get_ads")
    public ResponseEntity<AdsListResponse> getAllAds(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").ascending());
        Page<AdsResponse> adsPage = adsService.getAllAds(keyword, pageRequest);
        int totalPages = adsPage.getTotalPages();
        List<AdsResponse> adsList = adsPage.getContent();
        return ResponseEntity.ok(AdsListResponse.builder()
                .adsList(adsList).totalPages(totalPages).build());
    }

    @GetMapping("/ads_admin")
    public ResponseEntity<AdsListResponse> getAllAdsAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").ascending());
        Page<AdsResponse> adsPage = adsService.getAllAdsAdmin(pageRequest);
        int totalPages = adsPage.getTotalPages();
        List<AdsResponse> adsList = adsPage.getContent();
        return ResponseEntity.ok(AdsListResponse.builder()
                .adsList(adsList).totalPages(totalPages).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAds(@PathVariable int id, @RequestBody AdsDTO adsDTO) throws Exception {
        try{
            adsService.updateAds(id, adsDTO);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update_ads_payment")
    public ResponseEntity<?> updateAdsPayment(@RequestBody Map<String, String> request) throws Exception {
        try{
            String trading_code= request.get("trading_code");
            Ads ads= adsService.updateAdsPayment(trading_code);
            return ResponseEntity.ok(AdsResponse.fromAds(ads));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createAds(@Valid @RequestBody AdsDTO adsDTO, BindingResult result) throws DataNotFoundException {

        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(Collections.singletonMap("errors", String.join(", ", errorsMessage)));
        }
        Ads ads = adsService.createAds(adsDTO);
        return ResponseEntity.ok(AdsResponse.fromAds(ads));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAds(@PathVariable int id) {
        adsService.deleteAds(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdsById(
            @PathVariable("id") int adsId
    ){
        try {
            Ads existingAds = adsService.getAdsById(adsId);
            return ResponseEntity.ok(adsService.mapToAdsResponse(existingAds));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/send_trading_code")
    public ResponseEntity<?> sendTradingCode(@RequestBody Map<String, String> request) {
        try{
            String email = request.get("email");
            String trading_code = request.get("trading_code");
            clientService.sendTradingCode(trading_code, email);
            Map<String, String> response = new HashMap<>();
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/check_trading_code")
    public ResponseEntity<?> checkTradingCode(@RequestBody Map<String, String> request) {
        try{
            String trading_code = request.get("trading_code");
            Ads ads= adsService.checkTradingCode(trading_code);
            return ResponseEntity.ok(AdsResponse.fromAds(ads));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value= "upload_ads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAds (
            @PathVariable("id") int adsId,
            @ModelAttribute("files") List<MultipartFile> files){

        try {
            Ads existingAds= adsService.getAdsById(adsId);
            List<AdsImage> listAdsImg= adsImageRepository.findByAds(existingAds);
            files = files == null ? new ArrayList<MultipartFile>() : files;

            List<String> filenames = new ArrayList<>();

            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue; // Skip empty files
                }
                if (file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
                }
                String filename = storeFile(file);
                filenames.add(filename); // Add filename to the list
            }
            return ResponseEntity.ok("Upload Success Banner Ads");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "upload_image_ads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadBannerAdsFromCreate(@Valid @ModelAttribute("files") List<MultipartFile> files) {
        try {
            files = files == null ? new ArrayList<MultipartFile>() : files;

            List<String> filenames = new ArrayList<>();

            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue; // Skip empty files
                }
                if (file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
                }
                String filename = storeFile(file);
                filenames.add(filename); // Add filename to the list
            }

            // Create a JSON response with the list of filenames
            Map<String, Object> response = new HashMap<>();
            response.put("filenames", filenames);
            return ResponseEntity.ok().body(response);

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