package com.nicolyott.cineTrail.repository;

import com.nicolyott.cineTrail.entity.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
}
