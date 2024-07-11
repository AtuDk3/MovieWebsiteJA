package com.project.MovieWebsite.services;

import com.project.MovieWebsite.dtos.CountryDTO;
import com.project.MovieWebsite.models.Country;
import java.util.List;

public interface CountryService {

    /**
 * Creates a new country entity based on the provided CountryDTO object.
 *
 * @param countryDTO Data Transfer Object containing the details of the country to be created.
 * @return The created Country entity.
 */
Country createCountry(CountryDTO countryDTO);

/**
 * Retrieves a country entity by its unique identifier.
 *
 * @param countryId The unique identifier of the country to be retrieved.
 * @return The Country entity with the specified ID.
 */
Country getCountryById(int countryId);

/**
 * Retrieves a list of all country entities.
 *
 * @return A list of all Country entities.
 */
List<Country> getAllCountry();

/**
 * Updates the details of an existing country entity based on the provided CountryDTO object.
 *
 * @param countryId The unique identifier of the country to be updated.
 * @param countryDTO Data Transfer Object containing the updated details of the country.
 * @return The updated Country entity.
 */
Country updateCountry(int countryId, CountryDTO countryDTO);

/**
 * Deletes a country entity by its unique identifier.
 *
 * @param countryId The unique identifier of the country to be deleted.
 */
void deleteCountry(int countryId);

}
