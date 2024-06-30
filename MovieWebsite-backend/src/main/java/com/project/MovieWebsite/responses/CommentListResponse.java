package com.project.MovieWebsite.responses;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class CommentListResponse {

    private List<CommentResponse> comments;
    private int totalPages;
}