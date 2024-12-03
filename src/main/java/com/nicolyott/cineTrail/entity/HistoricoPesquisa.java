package com.nicolyott.cineTrail.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "historicoPesquisas")
public class HistoricoPesquisa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pesquisa;
    private LocalDateTime dataPesquisa;

    public HistoricoPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
        this.dataPesquisa = LocalDateTime.now();
    }

}
