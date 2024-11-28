package com.nicolyott.cineTrail.repository;

import com.nicolyott.cineTrail.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
