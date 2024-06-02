
package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.MovieDTO;
import com.project.MovieWebsite.models.Movie;
import com.project.MovieWebsite.repositories.MovieRepository;
import com.project.MovieWebsite.responses.MovieListResponse;
import com.project.MovieWebsite.responses.MovieResponse;
import com.project.MovieWebsite.services.MovieService;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieRepository movieRepository;

    @PostMapping("")
    public ResponseEntity<?> createMovie(@Valid @RequestBody MovieDTO movieDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        try{
            movieService.createMovie(movieDTO);
            return ResponseEntity.ok("Create movie type successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/genres")
    public ResponseEntity<MovieListResponse> getMovieByGenreId(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "genre_id") int genreId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").descending());
        Page<MovieResponse> moviePage = movieService.getAllMoviesByGenreId(keyword, genreId, pageRequest);
        int totalPages = moviePage.getTotalPages();
        List<MovieResponse> movies = moviePage.getContent();
        return ResponseEntity.ok(MovieListResponse.builder()
                .movies(movies).totalPages(totalPages).build());
    }

    @GetMapping("/countries")
    public ResponseEntity<MovieListResponse> getMovieByCountryId(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "country_id") int countryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").descending());
        Page<MovieResponse> moviePage = movieService.getAllMoviesByCountryId(keyword, countryId, pageRequest);
        int totalPages = moviePage.getTotalPages();
        List<MovieResponse> movies = moviePage.getContent();
        return ResponseEntity.ok(MovieListResponse.builder()
                .movies(movies).totalPages(totalPages).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(
            @PathVariable("id") int movieId
    ){
        try {
            Movie existingMovie = movieService.getMovieById(movieId);
            return ResponseEntity.ok(MovieResponse.fromMovie(existingMovie));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @GetMapping("/genres/{genreId}")
//    public ResponseEntity<?> getMovieByGenreId(@PathVariable("genreId") int genreId) {
//        try {
//            List<Movie> moviesByGenre = movieService.getMoviesByGenreId(genreId);
//            List<MovieResponse> movieResponses = moviesByGenre.stream()
//                    .map(MovieResponse::fromMovie)
//                    .collect(Collectors.toList());
//            return ResponseEntity.ok(movieResponses);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @GetMapping("/countries/{countryId}")
//    public ResponseEntity<?> getMovieByCountryId(@PathVariable("countryId") int countryId) {
//        try {
//            List<Movie> moviesByCountry = movieService.getMoviesByCountryId(countryId);
//            List<MovieResponse> movieResponses = moviesByCountry.stream()
//                    .map(MovieResponse::fromMovie)
//                    .collect(Collectors.toList());
//            return ResponseEntity.ok(movieResponses);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @PostMapping(value= "upload_movie/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAvatar (
            @PathVariable("id") int movieId,
            @Valid @ModelAttribute("file") MultipartFile file){

        try {
            Movie existingMovie= movieService.getMovieById(movieId);
            if (file != null) {
                if (file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
                }
                String filename = storeFile(file);
                existingMovie.setImage(filename);
                movieRepository.save(existingMovie);
            }
            return ResponseEntity.ok("Upload Success Image Movie");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName){
        try{
            Path imagePath= Paths.get("uploads/img_movie/"+imageName);
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
        Path uploadDir = Paths.get("uploads/img_movie");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
}
