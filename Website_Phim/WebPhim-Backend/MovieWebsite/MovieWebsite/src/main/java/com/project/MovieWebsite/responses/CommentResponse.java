package com.project.MovieWebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieWebsite.models.Comment;
import com.project.MovieWebsite.models.User;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CommentResponse {

    @JsonProperty("movie_id")
    private int movieId;

    @JsonProperty("user_response")
    private UserResponse userResponse;

    private String description;

    @JsonProperty("create_at")
    private Date createAt;

    public static List<CommentResponse> fromListComment(List<Comment> listComment){
        List<CommentResponse> listCommentResponse= new ArrayList<>();
        for(Comment comment: listComment) {
            CommentResponse commentResponse = CommentResponse.builder()
                    .movieId(comment.getMovie().getId())
                    .userResponse(UserResponse.fromUser(comment.getUser()))
                    .description(comment.getDescription())
                    .createAt(convertToDate(comment.getCreateAt()))
                    .build();
            listCommentResponse.add(commentResponse);
        }
        return listCommentResponse;
    }

    private static Date convertToDate(LocalDateTime dateTime) {
        return dateTime != null ? Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
}
