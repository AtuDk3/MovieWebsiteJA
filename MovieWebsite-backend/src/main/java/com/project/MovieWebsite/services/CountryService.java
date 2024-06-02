package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.CountryDTO;
import com.project.MovieWebsite.models.Country;
import java.util.List;

public interface CountryService {

    Country createCountry(CountryDTO countryDTO);

    Country getCountryById(int countryId);

    List<Country> getAllCountry();

    Country updateCountry(int countryId, CountryDTO countryDTO);

    void deleteCountry(int countryId);
}
