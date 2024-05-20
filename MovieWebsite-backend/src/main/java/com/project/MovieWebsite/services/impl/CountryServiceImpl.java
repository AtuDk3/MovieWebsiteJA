package com.project.MovieWebsite.services.impl;

import com.project.MovieWebsite.dtos.CountryDTO;
import com.project.MovieWebsite.models.Country;
import com.project.MovieWebsite.repositories.CountryRepository;
import com.project.MovieWebsite.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    @Override
    public Country createCountry(CountryDTO countryDTO) {
        Country newCountry = Country.builder().name(countryDTO.getName()).build();
        return countryRepository.save(newCountry);
    }

    @Override
    public Country getCountry(int id) {
        return countryRepository.findById(id).orElseThrow(() -> new RuntimeException("Country not found"));
    }

    @Override
    public List<Country> getAllCountry() {
        return countryRepository.findAll();
    }

    @Override
    public Country updateCountry(int id, CountryDTO countryDTO) {
        Country existingMovieType= getCountry(id);
        existingMovieType.setName(countryDTO.getName());
        //existingMovieType.setIsActive(movieTypeDTO.getIsActive());
        countryRepository.save(existingMovieType);
        return existingMovieType;
    }

    @Override
    public void deleteCountry(int id) {
        countryRepository.deleteById(id);
    }
}
