package com.project.MovieWebsite.models;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="tab_movie_views")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @JoinColumn(name = "view_date")
    private LocalDate viewDate;

    private int views;

    public MovieView(Movie movie, LocalDate viewDate, int views) {
        this.movie = movie;
        this.viewDate = viewDate;
        this.views = views;
    }
}
