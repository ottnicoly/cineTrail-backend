package com.nicolyott.cineTrail.entity;

import com.nicolyott.cineTrail.dto.FilmeDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "filmes")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String dataLancamento;
    private String popularidade;
    private String duracao;
    private String sinopse;
    private String poster;
    private Integer idTmdb;

    @ManyToOne
    private Favorito favorito;

    public Filme(FilmeDTO filmeDTO) {
        this.titulo = filmeDTO.titulo();
        this.dataLancamento = filmeDTO.dataLancamento();
        this.popularidade = filmeDTO.popularidade();
        this.sinopse = filmeDTO.sinopse();
        this.poster = filmeDTO.poster();
        this.idTmdb = filmeDTO.idTmdb();
    }

}
