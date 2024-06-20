
package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.text.Normalizer;
import java.util.Date;
import java.util.regex.Pattern;
import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

    @NotBlank(message = "Name movie required")
    private String name;

    @NotBlank(message = "Description required")
    private String description;

    @NotBlank(message = "Image required")
    private String image;

    private String slug;

    @JsonProperty("release_date")
    private Date releaseDate;

    @NotBlank(message = "Duration required")
    private String duration;

    @JsonProperty("id_genre")
    private int idGenre;

    @JsonProperty("id_movie_type")
    private int idMovieType;

    @JsonProperty("id_country")
    private int idCountry;

    @Min(value=1, message = "Episode must have more than 1")
    private int episode=1;

    @JsonProperty("is_fee")
    private int isFee=0;

    private int hot=0;

    @Min(value=1, message = "Session must have more than 1")
    private int season;

    @Min(value=13, message = "Limited age must have more than 13")
    @JsonProperty("limited_age")
    private int limitedAge;

    @JsonProperty("is_active")
    private int isActive=1;

    private String generateSlug(String name) {
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String slug = pattern.matcher(normalized).replaceAll("");

        slug = slug.toLowerCase().replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", "-");
        return slug;
    }

    @PostConstruct
    public void init() {
        if (this.slug == null || this.slug.isEmpty()) {
            this.slug = generateSlug(this.name);
        }
    }

}
