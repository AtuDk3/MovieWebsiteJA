package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.CountryDTO;
import com.project.MovieWebsite.models.Country;
import com.project.MovieWebsite.services.CountryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/countries")
public class CountryController {

    private final CountryService countryService;


    @GetMapping("")
    public ResponseEntity<List<Country>> getAllCountries() {
        List<Country> countries = countryService.getAllCountry();
        return ResponseEntity.ok(countries);
    }

    @PostMapping("")
    public ResponseEntity<?> createCountry(@Valid @RequestBody CountryDTO countryDTO, BindingResult result) {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        countryService.createCountry(countryDTO);
        return ResponseEntity.ok("Create country successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCountry(@PathVariable int id, @Valid @RequestBody CountryDTO countryDTO) {
        countryService.updateCountry(id, countryDTO);
        return ResponseEntity.ok("Update country successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable int id) {
        countryService.deleteCountry(id);
        return ResponseEntity.ok("Delete country successfully!");
    }
}
