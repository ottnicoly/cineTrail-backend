package com.nicolyott.cineTrail.controller;

import com.nicolyott.cineTrail.entity.favorito.CategoriaFavorito;
import com.nicolyott.cineTrail.entity.favorito.Favorito;
import com.nicolyott.cineTrail.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorito")
public class FavoritoController {

    @Autowired
    private FavoritoService service;

    @GetMapping()
    public List<Favorito> obterFavoritos(){
        return service.obterFavoritos();
    }

    @PostMapping("/salvar/{idTmdb}/{categoria}")
    public void adicionarAosFavoritos(@PathVariable Integer idTmdb, @PathVariable CategoriaFavorito categoria){
        service.adicionarAosFavoritos(idTmdb, categoria);
    }

    @DeleteMapping("/deletar/{idTmdb}")
    public void deletarFavorito(@PathVariable Integer idTmdb){
        service.deletarFavorito(idTmdb);
    }

    @GetMapping("/categoria/{categoriaFavorito}")
    public List<Favorito> buscarPorCategoria(@PathVariable String categoriaFavorito) {
        return service.buscarPorCategoria(categoriaFavorito);
    }

}
