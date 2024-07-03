
package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieWebsite.models.Genre;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.text.Normalizer;
import java.util.regex.Pattern;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GenreDTO {
    @NotEmpty(message = "Name can not empty!")
    private String name;

    private String slug;

    @NotEmpty(message = "Description can not empty!")
    private String description;

    @JsonProperty("is_active")
    private int isActive = 1;

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