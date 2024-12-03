package com.nicolyott.cineTrail.controller;

import com.nicolyott.cineTrail.dto.FilmeDTO;
import com.nicolyott.cineTrail.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("consulta")
public class FilmeController {

    @Autowired
    private FilmeService service;

    @GetMapping("{movie}")
    public List<FilmeDTO> pesquisaFilmeNome(@PathVariable("movie") String movie) {
        return service.buscarFilmeNome(movie);
    }

    @GetMapping("/em-alta")
    public List<FilmeDTO> obterFilmesEmAlta() {
        return service.obterFilmesEmAlta();
    }

    @GetMapping("/retorna-filme/{id}")
    public FilmeDTO obterFilmeId(@PathVariable("id") Integer idTmdb) {
        return service.buscarFilmeId(idTmdb);
    }

}
