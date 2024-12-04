package com.nicolyott.cineTrail.repository;

import com.nicolyott.cineTrail.entity.favorito.CategoriaFavorito;
import com.nicolyott.cineTrail.entity.favorito.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

   Favorito findByIdTmdb(Integer idTmdb);

   List<Favorito> findByCategoriaFavorito(CategoriaFavorito categoriaFavorito);

}
