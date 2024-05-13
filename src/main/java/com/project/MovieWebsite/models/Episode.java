package com.project.MovieWebsite.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tab_episode")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Episode extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@ManyToOne
//    @JoinColumn(name="movie_id")
//    private Movie movie;
//
    @Column(name="movie_url", nullable= false, length=255)
    private String movieUrl;


}
