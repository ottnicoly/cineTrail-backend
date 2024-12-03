package com.nicolyott.cineTrail.controller;

import com.nicolyott.cineTrail.service.HistoricoPesquisaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("historico")
public class HistoricoPesquisaController {

    @Autowired
    private HistoricoPesquisaService service;

    @DeleteMapping("/deletar/{pesquisa}")
    public void deletarHistorico(@PathVariable String pesquisa) {
        service.apagarHistorico(pesquisa);
    }




}
