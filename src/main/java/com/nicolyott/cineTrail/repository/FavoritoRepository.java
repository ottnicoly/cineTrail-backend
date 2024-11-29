package com.nicolyott.cineTrail.repository;

import com.nicolyott.cineTrail.entity.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
}
