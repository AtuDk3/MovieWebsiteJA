package com.project.MovieWebsite.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tab_comment")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@ManyToOne
//    @JoinColumn(name="movie_id")
//    private Movie movie;
//
//    @ManyToOne
//    @JoinColumn(name="user_id")
//    private User user;

    @Column(name="description", nullable= false)
    private String description;


}
