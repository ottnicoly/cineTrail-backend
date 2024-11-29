package com.nicolyott.cineTrail.entity;

import com.nicolyott.cineTrail.dto.FilmeDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "favoritos")
public class Favorito {

    @Id
    @GeneratedValue
    private Long id;
    @OneToMany
    private List<Filme> filmesFavoritos;

}
