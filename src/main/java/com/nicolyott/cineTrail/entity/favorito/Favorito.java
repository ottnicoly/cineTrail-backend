package com.nicolyott.cineTrail.entity.favorito;

import com.nicolyott.cineTrail.dto.FilmeDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "favoritos")
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer idTmdb;
    private String titulo;
    private LocalDateTime dataFavorito;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaFavorito categoriaFavorito;

    public Favorito(FilmeDTO filmeDTO, CategoriaFavorito categoriaFavorito){
        this.idTmdb = filmeDTO.idTmdb();
        this.titulo = filmeDTO.titulo();
        this.dataFavorito = LocalDateTime.now();
        this.categoriaFavorito = categoriaFavorito;

    }
}
