package com.nicolyott.cineTrail.model;

import jakarta.persistence.*;

@Entity
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String premiereDate;
    private String runtime;
    private String category;
    private String overview;
    private String poster;

    public Serie(){

    }

    public Serie(MovieData movieData) {
        this.title = movieData.title();
        this.premiereDate = movieData.premiereDate();
//        this.runtime = movieData.runtime();
//        this.category = movieData.category();
        this.overview = movieData.overview();
        this.poster = movieData.poster();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(String premiereDate) {
        this.premiereDate = premiereDate;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", premiereDate='" + premiereDate + '\'' +
                ", runtime='" + runtime + '\'' +
                ", category='" + category + '\'' +
                ", overview='" + overview + '\'' +
                ", poster='" + poster + '\'' +
                '}';
    }
}
