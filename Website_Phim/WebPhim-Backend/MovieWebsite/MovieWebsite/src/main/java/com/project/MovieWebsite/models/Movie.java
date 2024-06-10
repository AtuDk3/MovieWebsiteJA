package com.project.MovieWebsite.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name="tab_movie")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", nullable= false, length=255)
    private String name;

    @Column(name="description", nullable= false, length=255)
    private String description;

    @Column(name="image", nullable= false)
    private String image;

    private String slug;

    @Column(name="release_date", nullable= false)
    private Date releaseDate;

    @Column(name="duration", nullable= false, length=255)
    private String duration;

    @ManyToOne
    @JoinColumn(name="id_genre")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name="id_movie_type")
    private MovieType movieType;

    @ManyToOne
    @JoinColumn(name="id_country")
    private Country country;

    @Column(name="episode")
    private int episode;

    @Column(name="hot", nullable= false)
    private int hot;

    @Column(name="is_fee", nullable= false)
    private int isFee;

    @Column(name="season", nullable= false)
    private int season;

    @Column(name="limited_age", nullable= false)
    private int limitedAge;

    @Column(name="number_view", nullable= false)
    private int numberView;

    @Column(name="is_active")
    private int isActive;

    @PrePersist
    protected void onCreate() {
        this.releaseDate = new Date();
    }

}