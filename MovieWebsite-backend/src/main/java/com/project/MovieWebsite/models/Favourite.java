package com.project.MovieWebsite.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tab_favourite")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @ManyToOne
//    @JoinColumn(name="movie_id")
//    private Movie movie;
//
//    @ManyToOne
//    @JoinColumn(name="user_id")
//    private User user;


}
