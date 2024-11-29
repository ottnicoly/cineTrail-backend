package com.nicolyott.cineTrail.controller;

import com.nicolyott.cineTrail.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorito")
public class FavoritoController {

    @Autowired
    private FavoritoService service;

    @PostMapping("/salvar/{id}")
    public void adicionarAosFavoritos(@PathVariable Long id){
        service.adicionarAosFavoritos(id);
    }

}
