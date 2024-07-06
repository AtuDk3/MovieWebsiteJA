
package com.project.MovieWebsite.controllers;

import com.project.MovieWebsite.dtos.CountryDTO;
import com.project.MovieWebsite.models.Country;
import com.project.MovieWebsite.responses.CountryResponse;
import com.project.MovieWebsite.services.CountryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/countries")
public class CountryController {

    private final CountryService countryService;


    @GetMapping("")
    public ResponseEntity<List<CountryResponse>> getAllCountries() {
        List<Country> countries = countryService.getAllCountry();
        return ResponseEntity.ok(CountryResponse.fromListCountry(countries));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCountryById(@PathVariable int id) {
        try {
            Country existingCountry = countryService.getCountryById(id);
            return ResponseEntity.ok(CountryResponse.fromCountry(existingCountry));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateCountry(@PathVariable int id, @Valid @RequestBody CountryDTO countryDTO, BindingResult result) {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(Collections.singletonMap("errors", String.join(", ", errorsMessage)));
        }
        countryService.updateCountry(id, countryDTO);
        return ResponseEntity.ok(Collections.singletonMap("message", "Update country successfully!"));
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> createCountry(@Valid @RequestBody CountryDTO countryDTO, BindingResult result) {
        if (result.hasErrors()){
            List<String> errorsMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(Collections.singletonMap("errors", String.join(", ", errorsMessage)));
        }
        countryService.createCountry(countryDTO);
        return ResponseEntity.ok(Collections.singletonMap("message", "Create country successfully!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCountryById(@PathVariable int id) {
        countryService.deleteCountry(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Delete country successfully!"));
    }
}
