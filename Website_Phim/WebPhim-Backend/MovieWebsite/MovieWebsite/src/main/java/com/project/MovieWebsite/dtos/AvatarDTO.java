package com.project.MovieWebsite.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvatarDTO {

    @JsonProperty("url_image")
    private String urlImage;

    private MultipartFile file;
}
